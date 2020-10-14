package com.cc.base.handler;

import com.alibaba.fastjson.JSONException;
import com.cc.base.common.controller.IResultCode;
import com.cc.base.common.controller.ResultCodeEnum;
import com.cc.base.common.controller.ResultData;
import com.cc.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 全局异常拦截
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    public ResultData runtimeExceptionHandler(RuntimeException ex) {
        return rtnResultData(ResultCodeEnum.ERROR_RUNTIME_EXCEPTION, ex);
    }

    //空指针异常nullPointerExceptionHandler
    @ExceptionHandler(NullPointerException.class)
    public ResultData nullPointerExceptionHandler(NullPointerException ex) {
        return rtnResultData(ResultCodeEnum.ERROR_NULLPOINTER_EXCEPTION, ex);
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public ResultData classCastExceptionHandler(ClassCastException ex) {

        return rtnResultData(ResultCodeEnum.ERROR_CLASSCAST_EXCEPTION, ex);
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    public ResultData iOExceptionHandler(IOException ex) {
        return rtnResultData(ResultCodeEnum.ERROR_IO_EXCEPTION, ex);
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    public ResultData noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return rtnResultData(ResultCodeEnum.ERROR_NOSUCHMETHOD_EXCEPTION, ex);
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResultData indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return rtnResultData(ResultCodeEnum.ERROR_INDEXOUTOFBOUNDS_EXCEPTION, ex);
    }

    //数据格式化异常
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResultData dataParseException(HttpMessageNotReadableException ex) {
        return rtnResultData(ResultCodeEnum.ERROR_PARSE_EXCEPTION, ex);
    }

    //数据格式化异常
    @ExceptionHandler({JSONException.class})
    public ResultData jsonParseException(JSONException ex) {
        return rtnResultData(ResultCodeEnum.ERROR_PARSE_EXCEPTION, ex);
    }

    @ExceptionHandler({CannotCreateTransactionException.class})
    public ResultData exception(CannotCreateTransactionException ex) {
        log.error(ex.getMessage());
        return rtnResultData(ResultCodeEnum.ERROR_CREATE_TRANSACTION_EXCEPTION);
    }

    //自定义异常
    @ExceptionHandler({BaseException.class})
    public ResultData ValidateException(BaseException ex) {
        return rtnResultData(ex);
    }

    //其他错误
    @ExceptionHandler({Exception.class})
    public ResultData exception(Exception ex) {
        return rtnResultData(ResultCodeEnum.ERROR_SYSTEM_EXCEPTION, ex);
    }


    private ResultData rtnResultData(BaseException ex) {
        log.error(ex.getMessage());
        return rtnResultData(ex.getResultCode().getCode(), ex.getMessage(), "");
    }

    private ResultData rtnResultData(IResultCode resultCode) {
        log.error(resultCode.getMessage());
        return rtnResultData(resultCode.getCode(), resultCode.getMessage(), "");
    }

    private ResultData rtnResultData(ResultCodeEnum resultCode, Throwable ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return rtnResultData(resultCode.getCode(), resultCode.getMessage(), ex.getMessage());
    }

    private ResultData rtnResultData(String code, String message, String details) {
        ResultData resultData = new ResultData();
        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setDetails(details);
        return resultData;
    }

}
