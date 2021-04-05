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
     * token前缀
     */
    String REDIS_TOKEN_PREFIX = "token:";

    String AUTH_HEADER = "Authorization";

    String SECRET = "defaultSecret";

    int EXPIRATION = 604800;

    String JWT_SEPARATOR = "Bearer ";
}
