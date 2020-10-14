package com.cc.base.exception;


import com.cc.base.common.controller.ResultCodeEnum;

/**
 * 数据验证异常类
 */
public class ValidateException extends BaseException {

    public ValidateException(String message) {
        super(message);
    }

    @Override
    public ResultCodeEnum getResultCode() {
        return ResultCodeEnum.ERROR_VALIDATE_FAIL;
    }
}
