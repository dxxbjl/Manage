package io.github.yangyouwang.common.constant;

/**
 * @program: crud
 * @description: Jwt常量
 * @author: 杨有旺
 * @create: 2019-09-04 14:32
 **/
public interface JwtConstants {

    /**
     * token
     */
    String TOKEN = "token";
    /**
     * 根据key获取token
     */
    String AUTH_HEADER = "Authorization";
    /**
     * 密钥生成规则
     */
    String SECRET = "defaultSecret";
    /**
     * 过期时间
     */
    int EXPIRATION = 604800;
    /**
     * jwt前缀
     */
    String JWT_SEPARATOR = "Bearer ";
}
