package io.github.yangyouwang.core.interceptor;

import io.github.yangyouwang.common.annotation.ApiIdempotent;
import io.github.yangyouwang.common.constant.JwtConstants;
import io.github.yangyouwang.core.exception.CrudException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author yangyouwang
 * @title: ApiIdempotentInterceptor
 * @projectName crud
 * @description: 接口幂等性拦截器
 * @date 2021/4/210:17 PM
 */
@Component
public class ApiIdempotentInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        return check(request, response, handler);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(ApiIdempotent.class)) {
            //  幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
            String token = request.getParameter(JwtConstants.TOKEN);
            if (Strings.isBlank(token)) {
                throw new CrudException("请求幂等参数不存在");
            }
            Boolean flag = redisTemplate.hasKey(token);
            if (flag) {
              return redisTemplate.delete(token);
            }
            throw new CrudException("请求非幂等");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
