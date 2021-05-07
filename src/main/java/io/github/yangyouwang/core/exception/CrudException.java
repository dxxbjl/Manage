package io.github.yangyouwang.core.exception;

/**
 * @author yangyouwang
 * @title: CrudException
 * @projectName crud
 * @description: 框架异常
 * @date 2021/3/3010:10 PM
 */
public class CrudException extends RuntimeException {

    private static final long serialVersionUID = -3587103377836762876L;

    public CrudException(String msg) {
        super(msg);
    }

    public CrudException()
    {
        super();
    }
}
