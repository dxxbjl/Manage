package io.github.yangyouwang.core.exception;

import io.github.yangyouwang.common.enums.ResultStatus;

/**
 * @author yangyouwang
 * @title: CrudException
 * @projectName crud
 * @description: 框架异常
 * @date 2021/3/3010:10 PM
 */
public class CrudException extends RuntimeException {

    private static final long serialVersionUID = -3587103377836762876L;

    private ResultStatus resultStatus;

    public CrudException(ResultStatus resultStatus) {
        super(resultStatus.getMessage());
        this.resultStatus = resultStatus;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }
}
