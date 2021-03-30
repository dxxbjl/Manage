package io.github.yangyouwang.core.exception;

/**
 * @author yangyouwang
 * @title: UnauthorizedException
 * @projectName crud
 * @description: 自定义权限异常
 * @date 2021/3/3010:10 PM
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 3885400551304383736L;

    public UnauthorizedException(String msg)
    {
        super(msg);
    }

    public UnauthorizedException()
    {
        super();
    }
}
