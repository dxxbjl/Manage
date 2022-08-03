package io.github.yangyouwang.crud.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.properties.WeChatProperties;
import io.github.yangyouwang.core.util.JwtTokenUtil;
import io.github.yangyouwang.core.util.RestTemplateUtil;
import io.github.yangyouwang.crud.api.model.UserAuthDTO;
import io.github.yangyouwang.crud.api.model.UserAuthVO;
import io.github.yangyouwang.crud.app.entity.Oauth;
import io.github.yangyouwang.crud.app.entity.User;
import io.github.yangyouwang.crud.app.mapper.OauthMapper;
import io.github.yangyouwang.crud.app.mapper.UserMapper;
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
    private OauthMapper oauthMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = Throwable.class)
    public UserAuthVO userAuth(UserAuthDTO userAuthDTO) {
        String openId = "";
        UserAuthVO userAuthVO = new UserAuthVO();
        if (ConfigConsts.WX_APP_TYPE == userAuthDTO.getAppType()) {
            // 根据微信code获取openId
            String api = ConfigConsts.WEIXIN_OPENID_API.replace("APPID",weChatProperties.getAppID())
                    .replace("SECRET",weChatProperties.getAppSecret())
                    .replace("JSCODE",userAuthDTO.getCode());
            String res = RestTemplateUtil.get(api);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if (jsonObject.containsKey("openid")) {
                openId = jsonObject.getString("openid");
                userAuthVO.setSessionKey(jsonObject.getString("session_key"));
            } else {
                throw new RuntimeException("获取openId失败");
            }
        } else {
            throw new RuntimeException("授权类型错误");
        }
        Oauth oauth = oauthMapper.selectOne(new LambdaQueryWrapper<Oauth>()
                .eq(Oauth::getOpenId,openId).eq(Oauth::getAppType,userAuthDTO.getAppType()));
        if (Objects.isNull(oauth)) {
            User user = new User();
            user.setStatus(ConfigConsts.USER_STATUS_AVAILABLE);
            if (this.save(user)) {
                Long userId = user.getId();
                oauth = new Oauth();
                oauth.setUserId(userId);
                oauth.setOpenId(openId);
                oauth.setAppType(userAuthDTO.getAppType());
                if (oauthMapper.insert(oauth) > 0) {
                    userAuthVO.setToken(JwtTokenUtil.buildJWT(userId.toString()));
                    return userAuthVO;
                }
            }
            throw new RuntimeException("登陆失败");
        }
        userAuthVO.setToken(JwtTokenUtil.buildJWT(oauth.getUserId().toString()));
        return userAuthVO;
    }

}
