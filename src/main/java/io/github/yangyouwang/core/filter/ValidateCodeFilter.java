package io.github.yangyouwang.core.filter;

import com.alibaba.fastjson.JSON;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.common.domain.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        if (StringUtils.equals(Constants.DEFAULT_LOGIN_URL, request.getServletPath())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
            if (!validate(request,response)) {
                return;
            }
        }
        // 不是一个登录请求，不做校验 直接通过
        filterChain.doFilter(request, response);
    }

    /**
     * 验证码校验
     */
    private boolean validate(HttpServletRequest request,HttpServletResponse response) {
        String code = request.getParameter("code");
        if (StringUtils.isBlank(code)) {
            write(response,"验证码不能为空");
            return false;
        }
        Object checkCode = request.getSession(false).getAttribute(Constants.IMAGE_CODE_SESSION);
        if (null == checkCode) {
            write(response,"验证码不存在");
            return false;
        }
        if (!StringUtils.equalsIgnoreCase(code,checkCode.toString())) {
            write(response,"验证码不匹配");
            return false;
        }
        request.getSession(false).removeAttribute(Constants.IMAGE_CODE_SESSION);
        return true;
    }

    /**
     * 发送HTTP响应信息
     *
     * @param response HTTP响应对象
     * @param message 信息内容
     */
    private void write(HttpServletResponse response, String message) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter();) {
            writer.write(JSON.toJSONString(Result.failure(message)));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
