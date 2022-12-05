package io.github.yangyouwang.crud.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.github.yangyouwang.common.constant.ApiConsts;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.enums.AppOauthType;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.config.properties.QQProperties;
import io.github.yangyouwang.core.util.api.ApiContext;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.core.config.properties.WeChatProperties;
import io.github.yangyouwang.core.util.JwtTokenUtil;
import io.github.yangyouwang.core.util.RestTemplateUtil;
import io.github.yangyouwang.crud.api.factory.UserFactory;
import io.github.yangyouwang.crud.api.model.dto.*;
import io.github.yangyouwang.crud.api.model.vo.UserAuthVO;
import io.github.yangyouwang.crud.api.model.vo.UserInfoVO;
import io.github.yangyouwang.crud.api.model.vo.MpWxAuthVO;
import io.github.yangyouwang.crud.app.entity.Oauth;
import io.github.yangyouwang.crud.app.entity.User;
import io.github.yangyouwang.crud.app.mapper.UserMapper;
import io.github.yangyouwang.crud.app.service.OauthService;
import io.github.yangyouwang.crud.system.service.SysDictValueService;
import org.apache.commons.codec.CharEncoding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Objects;
/**
 * Description: 用户业务层 <br/>
 * date: 2022/8/3 20:46<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Service
public class ApiUserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private WeChatProperties weChatProperties;

    @Resource
    private OauthService oauthService;

    @Resource
    private SysDictValueService sysDictValueService;

    @Resource
    private ApiSmsCodeService apiSmsCodeService;

    @Resource
    private QQProperties qqProperties;

    /**
     * 微信小程序授权
     * @return 响应
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = Throwable.class)
    public MpWxAuthVO mpWxAuth(MpWxAuthDTO mpWxAuthDTO) {
        // 根据微信code获取openId
        String api = ApiConsts.WX_OPENID_API.replace("APPID",weChatProperties.getAppID())
                .replace("SECRET",weChatProperties.getAppSecret())
                .replace("JSCODE",mpWxAuthDTO.getCode());
        String res = RestTemplateUtil.get(api);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (jsonObject.containsKey("errcode")) {
            throw new RuntimeException("微信小程序授权失败");
        }
        MpWxAuthVO wxAuthVO = new MpWxAuthVO();
        String sessionKey = jsonObject.getString("session_key");
        String openId = jsonObject.getString("openid");
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>().eq(Oauth::getAppSecret,openId)
                .eq(Oauth::getAppType,AppOauthType.MP_WX.name()));
        if (Objects.nonNull(oauth)) {
            // 登录成功
            wxAuthVO.setSessionKey(sessionKey);
            wxAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
            return wxAuthVO;
        }
        // 登录成功
        User user = UserFactory.createUser(mpWxAuthDTO.getAvatarUrl(), mpWxAuthDTO.getNickName(), mpWxAuthDTO.getGender());
        this.save(user);
        oauthService.save(UserFactory.createOauth(user.getId(), openId, AppOauthType.MP_WX));
        wxAuthVO.setSessionKey(sessionKey);
        wxAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
        return wxAuthVO;
    }
    /**
     * 根据用户id获取用户详情
     * @return 响应
     */
    @Transactional(readOnly = true)
    public UserInfoVO getUserInfo() {
        Long userId = ApiContext.getUserId();
        User user = getById(userId);
        Assert.notNull(user, "用户不存在");
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user,userInfoVO);
        // 性别
        String sex = sysDictValueService.getDictValueName(ConfigConsts.DICT_KEY_SEX, user.getGender().toString());
        userInfoVO.setGenderLabel(sex);
        return userInfoVO;
    }
    /**
     * 用户名密码授权
     * @return 响应
     */
    public UserAuthVO passwordAuth(PasswordAuthDTO passwordAuthDTO) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, passwordAuthDTO.getMobile()));
        Assert.notNull(user, "用户不存在");
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>()
                .eq(Oauth::getUserId, user.getId()).eq(Oauth::getAppType,AppOauthType.PASSWORD.name()));
        if (!oauth.getAppSecret().equals(passwordAuthDTO.getAppSecret())) {
            throw new CrudException(ResultStatus.LOGIN_ERROR);
        }
        UserAuthVO userAuthVO = new UserAuthVO();
        userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
        return userAuthVO;
    }

    /**
     * 解密微信用户信息
     * @param wxUserInfoDTO 加密微信用户信息
     * @return 手机号
     */
    public String decodeWxUser(WxUserInfoDTO wxUserInfoDTO) {
        String userPhoneNumber = getUserPhoneNumber(wxUserInfoDTO.getSessionKey(), wxUserInfoDTO.getIv(), wxUserInfoDTO.getEncryptedData());
        Long userId = ApiContext.getUserId();
        User user = new User();
        user.setId(userId);
        user.setMobile(userPhoneNumber);
        this.updateById(user);
        return userPhoneNumber;
    }
    /**
     * 根据微信密钥获取用户手机号
     * @param sessionKey 加密秘钥
     * @param iv 偏移量
     * @param encryptedData 被加密的数据
     * @return 用户手机号
     */
    private String getUserPhoneNumber(String sessionKey,String iv,String encryptedData) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(result);
                return jsonObject.getString("phoneNumber");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取手机号失败");
        }
        return null;
    }
    /**
     * 手机号验证码授权
     * @return 响应
     */
    public UserAuthVO mobileAuth(MobileAuthDTO mobileAuthDTO) {
        String mobile = mobileAuthDTO.getMobile();
        String code = mobileAuthDTO.getCode();
        apiSmsCodeService.checkMobileCode(mobile,code);
        // 响应
        UserAuthVO userAuthVO = new UserAuthVO();
        // 查询用户
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>().eq(Oauth::getAppSecret,mobile)
                .eq(Oauth::getAppType,AppOauthType.PHONE.name()));
        if (Objects.nonNull(oauth)) {
            // 登录成功
            userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
            return userAuthVO;
        }
        // 登录成功
        User user = UserFactory.createUser(mobile);
        this.save(user);
        oauthService.save(UserFactory.createOauth(user.getId(),mobile,AppOauthType.PHONE));
        userAuthVO.setToken(JwtTokenUtil.buildJWT(user.getId().toString()));
        return userAuthVO;
    }
    /**
     * 更新用户信息
     * @param userInfoDTO 用户信息
     * @return 响应
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean modifyUser(UserInfoDTO userInfoDTO) {
        Long userId = ApiContext.getUserId();
        User user = getById(userId);
        Assert.notNull(user, "用户不存在");
        BeanUtils.copyProperties(userInfoDTO,user);
        return updateById(user);
    }
    /**
     * 获取QQ授权code
     * @return 响应
     */
    public String getQQCode() {
        String api = ApiConsts.QQ_CODE_API.replace("CLIENTID",qqProperties.getAppID());
        try {
            api.replace("REDIRECTURI", URLEncoder.encode(qqProperties.getRedirectUrl(), CharEncoding.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("解析回调地址出错");
        }
        return RestTemplateUtil.get(api);
    }
    /**
     * QQ授权回调
     * @param qqAuthDTO QQ授权对象
     * @return 授权秘钥
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = Throwable.class)
    public UserAuthVO qqAuthCallback(QQAuthDTO qqAuthDTO) {
        // 获取token
        String authApi = ApiConsts.QQ_AUTH_API.replace("CLIENTID",qqProperties.getAppID())
                .replace("CLIENTSECRET",qqProperties.getAppSecret())
                .replace("CODE",qqAuthDTO.getCode());
        String redirectUrl = qqProperties.getRedirectUrl();
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("解析回调地址出错");
        }
        authApi.replace("REDIRECTURI",redirectUrl);
        String token = RestTemplateUtil.get(authApi);
        // 获取openId
        String openIdApi = ApiConsts.QQ_OPENID_API.replace("ACCESSTOKEN", token);
        String openId = RestTemplateUtil.get(openIdApi);
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>().eq(Oauth::getAppSecret, openId)
                .eq(Oauth::getAppType, AppOauthType.QQ.name()));
        UserAuthVO userAuthVO = new UserAuthVO();
        if (Objects.nonNull(oauth)) {
            // 登录成功
            userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
            return userAuthVO;
        }
        // 获取用户信息
        String userApi = ApiConsts.QQ_GET_USER_INFO_API.replace("APPID", qqProperties.getAppID())
                .replace("OPENID", openId);
        JSONObject jsonObject = JSONObject.parseObject(RestTemplateUtil.get(userApi));
        String avatar = jsonObject.getString("figureurl_qq");
        String nickname = jsonObject.getString("nickname");
        Integer gender = jsonObject.getInteger("gender");
        // 登录成功
        User user = UserFactory.createUser(avatar, nickname, gender);
        this.save(user);
        oauthService.save(UserFactory.createOauth(user.getId(), openId, AppOauthType.QQ));
        userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
        return userAuthVO;
    }
    /**
     * 微信APP授权
     * @return 响应
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = Throwable.class)
    public UserAuthVO wxAuth(WxAuthDTO wxAuthDTO) {
        String authApi = ApiConsts.WX_AUTH_API.replace("APPID",weChatProperties.getAppID())
                .replace("SECRET",weChatProperties.getAppSecret())
                .replace("CODE",wxAuthDTO.getCode());
        String authRes = RestTemplateUtil.get(authApi);
        JSONObject authJsonObject = JSONObject.parseObject(authRes);
        if(authJsonObject.containsKey("errcode")) {
            throw new RuntimeException("微信授权失败");
        }
        String openId = authJsonObject.getString("openid");
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>().eq(Oauth::getAppSecret,openId)
                .eq(Oauth::getAppType,AppOauthType.WX_APP.name()));
        UserAuthVO userAuthVO = new UserAuthVO();
        if (Objects.nonNull(oauth)) {
            // 登录成功
            userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
            return userAuthVO;
        }
        // 获取微信用户信息
        String accessToken = authJsonObject.getString("access_token");
        String userApi = ApiConsts.WX_GET_USER_INFO_API.replace("ACCESSTOKEN",accessToken)
                .replace("OPENID",openId);
        String userRes = RestTemplateUtil.get(userApi);
        JSONObject userJsonObject = JSONObject.parseObject(userRes);
        if(userJsonObject.containsKey("errcode")) {
            throw new RuntimeException("获取微信用户信息失败");
        }
        String avatar = userJsonObject.getString("headimgurl");
        String nickname = userJsonObject.getString("nickname");
        Integer gender = userJsonObject.getInteger("sex");
        // 登录成功
        User user = UserFactory.createUser(avatar, nickname, gender);
        this.save(user);
        oauthService.save(UserFactory.createOauth(user.getId(), openId, AppOauthType.WX_APP));
        userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
        return userAuthVO;
    }

    /**
     * 修改密码
     * @param modifyPasswordDTO 修改密码DTO
     * @return 响应
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = Throwable.class)
    public boolean modifyPassword(ModifyPasswordDTO modifyPasswordDTO) {
        String mobile = modifyPasswordDTO.getMobile();
        String code = modifyPasswordDTO.getCode();
        String passwordOne = modifyPasswordDTO.getPasswordOne();
        String passwordTwo = modifyPasswordDTO.getPasswordTwo();
        if (!passwordOne.equals(passwordTwo)) {
            throw new RuntimeException("输入两次密码不一致，修改密码失败");
        }
        //校验手机验证码
        apiSmsCodeService.checkMobileCode(mobile,code);
        // 获取当前用户
        Long userId = ApiContext.getUserId();
        User user = this.getById(userId);
        Assert.notNull(user, "用户不存在");
        if (!mobile.equals(user.getMobile())) {
            throw new RuntimeException("当前绑定手机号不一致，修改密码失败");
        }
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>()
                .eq(Oauth::getUserId, user.getId()).eq(Oauth::getAppType,AppOauthType.PASSWORD.name()));
        Assert.notNull(oauth, "未设置用户密码");
        if (!oauth.getAppSecret().equals(passwordOne)) {
            throw new RuntimeException("旧密码输入错误");
        }
        oauth.setAppSecret(passwordTwo);
        return oauthService.updateById(oauth);
    }
}
