package com.newframe.core.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newframe.core.web.constant.RespStausEnum;

/**
 * Created by xm on 2016/4/2.
 */
public class ResponseVO {
    private RespStausEnum status;
    private String message;
    private Object data;
    private Boolean success;

    public RespStausEnum getStatus() {
        return status;
    }

    public void setStatus(RespStausEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static ResponseVO newSuccess(String message, Object data) {
        ResponseVO vo = new ResponseVO();
        vo.setStatus(RespStausEnum.SUCCESS);
        vo.setMessage(message);
        vo.setData(data);
        vo.setSuccess(true);
        return vo;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
