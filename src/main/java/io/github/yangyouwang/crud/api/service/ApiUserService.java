package io.github.yangyouwang.crud.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.core.properties.WeChatProperties;
import io.github.yangyouwang.core.util.JwtTokenUtil;
import io.github.yangyouwang.core.util.RestTemplateUtil;
import io.github.yangyouwang.crud.api.model.*;
import io.github.yangyouwang.crud.app.entity.Oauth;
import io.github.yangyouwang.crud.app.entity.User;
import io.github.yangyouwang.crud.app.mapper.UserMapper;
import io.github.yangyouwang.crud.app.service.OauthService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = Throwable.class)
    public WxAuthVO wxAuth(WxAuthDTO wxAuthDTO) {
        // 根据微信code获取openId
        String api = ConfigConsts.WEIXIN_OPENID_API.replace("APPID",weChatProperties.getAppID())
                .replace("SECRET",weChatProperties.getAppSecret())
                .replace("JSCODE",wxAuthDTO.getCode());
        String res = RestTemplateUtil.get(api);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (jsonObject.containsKey("errcode")) {
            throw new CrudException(ResultStatus.GET_OPENID_ERROR);
        }
        WxAuthVO wxAuthVO = new WxAuthVO();
        String openId = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>().eq(Oauth::getAppSecret,openId).eq(Oauth::getAppType,ConfigConsts.WX_APP_TYPE));
        if (Objects.nonNull(oauth)) {
            // // 登录成功
            wxAuthVO.setSessionKey(sessionKey);
            wxAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
            return wxAuthVO;
        }
        User user = new User();
        user.setStatus(ConfigConsts.USER_STATUS_AVAILABLE);
        this.save(user);
        Oauth newOauth = new Oauth();
        newOauth.setUserId(user.getId());
        newOauth.setAppSecret(openId);
        newOauth.setAppType(ConfigConsts.WX_APP_TYPE);
        oauthService.save(newOauth);
        // 登录成功
        wxAuthVO.setSessionKey(sessionKey);
        wxAuthVO.setToken(JwtTokenUtil.buildJWT(newOauth.getUserId().toString()));
        return wxAuthVO;
    }
    /**
     * 根据用户id获取用户详情
     * @return 响应
     */
    public UserInfoVO getUserInfoById(Long userId) {
        User user = getById(userId);
        if (Objects.isNull(user)) {
            throw new CrudException(ResultStatus.USER_NO_EXIST_ERROR);
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user,userInfoVO);
        return userInfoVO;
    }
    /**
     * 用户名密码授权
     * @return 响应
     */
    public UserAuthVO passwordAuth(PasswordAuthDTO passwordAuthDTO) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, passwordAuthDTO.getMobile()));
        if (Objects.isNull(user)) {
            throw new CrudException(ResultStatus.USER_NO_EXIST_ERROR);
        }
        Oauth oauth = oauthService.getOne(new LambdaQueryWrapper<Oauth>()
                .eq(Oauth::getUserId, user.getId()).eq(Oauth::getAppType,ConfigConsts.PASSWORD_APP_TYPE));
        if (!oauth.getAppSecret().equals(passwordAuthDTO.getAppSecret())) {
            throw new CrudException(ResultStatus.LOGIN_ERROR);
        }
        UserAuthVO userAuthVO = new UserAuthVO();
        userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
        return userAuthVO;
    }
}
