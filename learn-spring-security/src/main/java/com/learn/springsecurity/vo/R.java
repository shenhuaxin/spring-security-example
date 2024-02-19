package com.learn.springsecurity.vo;


import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;

@Data
public class R<T> implements Serializable {

    /**
     * 默认成功消息
     */
    private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    private static final String DEFAULT_FAILURE_MESSAGE = "操作失败";

    private static final int SUCCESS_CODE = 200;

    /**
     * 返回代码
     */
    private Integer code = 200;

    /**
     * 返回处理消息
     */
    private String message = "";

    /**
     * 返回数据对象 data
     */
    private T data;

    private R(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static <T> R<T> data(T data) {
        R<T> res = new R<>(SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
        res.data = data;
        return res;
    }

    public static <T> R<T> success() {
        R<T> res = new R<>(SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
        return res;
    }
    public static <T> R<T> success(String msg) {
        R<T> res = new R<>(SUCCESS_CODE, msg);
        return res;
    }

    public static <T> R<T> fail(int code) {
        R<T> res = new R<>(code, DEFAULT_FAILURE_MESSAGE);
        return res;
    }

    public static <T> R<T> fail(int code, String msg) {
        R<T> res = new R<>(code, msg);
        return res;
    }


}
