package com.newframe.core.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.newframe.core.web.constant.RespStausEnum;

import java.util.List;

/**
 * Created by xm on 2016/4/2.
 */
public class ErrorVO extends ResponseVO {
    private List<String> traces;
    private String code;

    public List<String> getTraces() {
        if (traces == null) {
            traces = Lists.newArrayList();
        }
        return traces;
    }

    public void setTraces(List<String> traces) {
        this.traces = traces;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ErrorVO newError(String message, String code, Object data) {
        ErrorVO vo = new ErrorVO();
        vo.setStatus(RespStausEnum.SUCCESS);
        vo.setMessage(message);
        vo.setData(data);
        vo.setSuccess(false);
        vo.setCode(code);
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
