package io.github.yangyouwang.core.filter;

import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.core.exception.CrudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangyouwang
 * @title: ValidateCodeFilter
 * @projectName crud
 * @description: 验证码过滤器
 * @date 2021/5/809:17 PM
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 登陆请求
        if (StringUtils.equals("/login", request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
            validate(request);
        }
        // 不是一个登录请求，不做校验 直接通过
        filterChain.doFilter(request, response);
    }

    /**
     * 验证码校验
     */
    private void validate(HttpServletRequest request) {
        String code = request.getAttribute("code").toString();
        if (StringUtils.isBlank(code)) {
            throw new CrudException("验证码不能为空");
        }
        String checkCode = request.getSession().getAttribute(Constants.IMAGE_CODE_SESSION).toString();
        if (checkCode == null) {
            throw new CrudException("验证码不存在");
        }
        if (!StringUtils.equals(code, checkCode)) {
            throw new CrudException("验证码不匹配");
        }
        request.getSession().removeAttribute(Constants.IMAGE_CODE_SESSION);
    }
}
