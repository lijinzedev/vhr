package com.li.vhr.result;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  ResultStatus implements ResultProvide {
    SUCCESS(200, "登录成功"),
    NOTLOGIN(401, "尚未登录,请登录"),
    LOGINOUT(200, "注销成功"),
    ERROR(500, "登录失败");

    private Integer status;
    private String msg;

    ResultStatus(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }


    @Override
    public Integer getStatus() {
        return status;
    }
}
