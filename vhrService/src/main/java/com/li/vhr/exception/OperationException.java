package com.li.vhr.exception;

public class OperationException extends RuntimeException {
    private String msg;

    public OperationException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
