package com.li.vhr.result;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/8/6
 */

public class ResultBean<T> {
    private ResultProvide status;
    private T data;


    public ResultBean() {
    }

    public ResultBean(ResultProvide resultProvide, T t) {
        this.status = resultProvide;
        this.data = t;
    }

    public ResultProvide getStatus() {
        return status;
    }

    public void setStatus(ResultProvide status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResultBean<T> ok() {
        return ok(null);
    }

    public static <T> ResultBean<T> ok(ResultProvide errorToResult) {
        return new ResultBean<>(errorToResult, null);
    }

    public static <T> ResultBean<T> ok(ResultProvide errorToResult, T t) {
        return new ResultBean<>(errorToResult, t);
    }

    public static <T> ResultBean<T> ok(T t) {
        return new ResultBean<>(ResultStatus.SUCCESS, t);
    }

    public static <T> ResultBean<T> error() {
        return error(null);
    }

    public static <T> ResultBean<T> error(T t) {
        return new ResultBean<>(ResultStatus.ERROR, t);
    }
}
