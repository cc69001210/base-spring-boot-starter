package com.cc.base.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 返回成功结果集
     *
     * @return
     */
    protected ResultData success() {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultData.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return resultData;
    }

    /**
     * 返回成功结果集
     *
     * @param message 指定返回消息
     * @return
     */
    protected ResultData success(String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultData.setMessage(message);
        return resultData;
    }

    /**
     * 返回成功结果集
     *
     * @param data 返回的数据结果集
     * @return
     */
    protected ResultData success(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultData.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        resultData.setData(data);
        return resultData;
    }

    /**
     * 返回成功结果集
     *
     * @param message 指定返回消息
     * @param data    返回的数据结果集
     * @return
     */
    protected ResultData success(Object data,String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultData.setMessage(message);
        resultData.setData(data);
        return resultData;
    }

    /**
     * 指定类型返回结果
     *
     * @param resultCode
     * @return
     */
    protected ResultData failed(IResultCode resultCode) {
        ResultData resultData = new ResultData();
        resultData.setCode(resultCode.getCode());
        if (!StringUtils.isEmpty(resultCode.getMessage())) {
            resultData.setMessage(resultCode.getMessage());
        } else {
            resultData.setMessage("未知错误信息！");
        }
        return resultData;
    }

    /**
     * 指定类型返回结果
     *
     * @param resultCode
     * @param message
     * @return
     */
    protected ResultData failed(IResultCode resultCode, String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(resultCode.getCode());
        if (StringUtils.isEmpty(message)) {
            message = resultCode.getMessage();
        }
        if (!StringUtils.isEmpty(message)) {
            resultData.setMessage(message);
        } else {
            resultData.setMessage("未知错误信息！");
        }
        return resultData;
    }

    /**
     * 指定类型返回结果
     * @param resultCode
     * @param message
     * @return
     */
    protected ResultData failed(IResultCode resultCode, String message,String details) {
        ResultData resultData = new ResultData();
        resultData.setCode(resultCode.getCode());
        if (StringUtils.isEmpty(message)) {
            message = resultCode.getMessage();
        }
        if (!StringUtils.isEmpty(message)) {
            resultData.setMessage(message);
        } else {
            resultData.setMessage("未知错误信息！");
        }
        resultData.setDetails(details);
        return resultData;
    }
}
