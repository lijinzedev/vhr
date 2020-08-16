package com.li.vhr.api;

// 自定义返回消息类型
public class ResultWarrp<T> {
    private T data;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
