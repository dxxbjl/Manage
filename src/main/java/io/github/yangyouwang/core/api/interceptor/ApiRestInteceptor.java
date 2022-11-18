package io.github.yangyouwang.core.api.interceptor;

import io.github.yangyouwang.common.annotation.ApiIdempotent;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.constant.CacheConsts;
import io.github.yangyouwang.common.constant.JwtConstants;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.core.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: crud
 * @description: Rest Api接口鉴权
 * @author: yangyouwang
 * @create: 2019-09-04 14:31
 **/
@Component
public class ApiRestInteceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        return checkJwt(request, handler) && checkApiIdempotent(request, handler);
    }

    private boolean checkJwt(HttpServletRequest request, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod)handler ;
        Method method = handlerMethod.getMethod() ;
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 获取 HTTP HEAD 中的 TOKEN
        String authorization = request.getHeader(JwtConstants.AUTH_HEADER);
        // 校验 TOKEN
        if (StringUtils.isNotBlank(authorization) &&
                authorization.startsWith(JwtConstants.JWT_SEPARATOR)) {
            boolean flag = JwtTokenUtil.checkJWT(authorization);
            if (flag) {
                JwtTokenUtil.parseJWT(authorization);
                return true;
            }
        }
        throw new CrudException(ResultStatus.NO_PERMISSION);
    }

    private boolean checkApiIdempotent(HttpServletRequest request, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(ApiIdempotent.class)) {
            //  幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
            String token = request.getParameter(JwtConstants.TOKEN);
            if (Strings.isBlank(token)) {
                throw new CrudException(ResultStatus.IDEMPOTENT_NOT_EXIST);
            }
            String tokenKey = CacheConsts.REDIS_TOKEN_PREFIX + token;
            boolean flag = redisTemplate.hasKey(tokenKey);
            if (flag) {
                return redisTemplate.delete(tokenKey);
            }
            throw new CrudException(ResultStatus.NON_IDEMPOTENT);
        }
        return true;
    }
}
