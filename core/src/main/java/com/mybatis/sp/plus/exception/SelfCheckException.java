package com.mybatis.sp.plus.exception;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 22:35
 */
public class SelfCheckException extends Exception {

    public SelfCheckException() {
    }

    public SelfCheckException(String message) {
        super(message);
    }
}
