package io.github.yangyouwang.core.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.security.authentication.event.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * @author yangyouwang
 * @title: StringUtil
 * @projectName crud
 * @description: 字符串工具类
 * @date 2021/3/312:34 PM
 */
public class StringUtil {
    /**
     * URL参数转MAP
     * @param paramStr 参数
     * @return 返回Map
     */
    public static Map<String, Object> paramToMap(String paramStr) {
        String[] params = paramStr.split("&");
        Map<String, Object> resMap = new HashMap<String, Object>();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = param[1];
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                try {
                    resMap.put(key, URLDecoder.decode(value,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return resMap;
    }
    /**
     * html转义
     * @param html HTML代码
     * @return html字符串
     */
    public static String toHtml(String html) {
        return StringEscapeUtils.unescapeHtml4(html).replaceAll("figure","div");
    }
    /**
     * 获取字符串中id数组
     * @param ids ids字符串
     * @return ids 数组
     */
    public static Long[] getId(String ids) {
        //截取字符串
        String[] idsArr = ids.split(",");
        //转换long类型的数组
        return (Long[]) ConvertUtils.convert(idsArr,Long.class);
    }
    /**
     * 产生4位随机字符串
     */
    public static String getCheckCode() {
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 登录异常信息
     * @param event 事件
     * @return 异常信息
     */
    public static String getAuthenticationFailureMessage(AbstractAuthenticationFailureEvent event) {
        String message;
        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            //提供的凭据是错误的，用户名或者密码错误
            message = "提供的凭据是错误的，用户名或者密码错误";
        } else if (event instanceof AuthenticationFailureCredentialsExpiredEvent) {
            //验证通过，但是密码过期
            message = "验证通过，但是密码过期";
        } else if (event instanceof AuthenticationFailureDisabledEvent) {
            //验证过了但是账户被禁用
            message = "验证过了但是账户被禁用";
        } else if (event instanceof AuthenticationFailureExpiredEvent) {
            //验证通过了，但是账号已经过期
            message = "验证通过了，但是账号已经过期";
        } else if (event instanceof AuthenticationFailureLockedEvent) {
            //账户被锁定
            message = "账户被锁定";
        } else if (event instanceof AuthenticationFailureProviderNotFoundEvent) {
            //配置错误，没有合适的AuthenticationProvider来处理登录验证
            message = "配置错误";
        } else if (event instanceof AuthenticationFailureProxyUntrustedEvent) {
            // 代理不受信任，用于Oauth、CAS这类三方验证的情形，多属于配置错误
            message = "代理不受信任";
        } else if (event instanceof AuthenticationFailureServiceExceptionEvent) {
            // 其他任何在AuthenticationManager中内部发生的异常都会被封装成此类
            message = "内部发生的异常";
        } else {
            message = "其他未知错误";
        }
        return message;
    }
}
