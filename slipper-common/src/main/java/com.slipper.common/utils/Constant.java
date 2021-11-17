package com.slipper.common.utils;

/**
 * 常量工具类
 *
 * @author gumingchen
 * @email 1240235512@qq.com
 * @date 1995-08-30 00:00:00
 */
public class Constant {

    /**
     * token 键值
     */
    public static final String TOKEN_KEY = "im-token";
    /**
     * 最大连接数
     */
    public static final int MAX_CONTENT_LENGTH = 65536;

    /**
     * 状态码 异常信息
     */
    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_STATUS = "success";
    public static final String SUCCESS_MESSAGE = "成功!";
    public static final int ERROR_CODE = 500;
    public static final String ERROR_STATUS = "error";
    public static final String ERROR_MESSAGE = "未知异常!";
    public static final int VERIFICATION_ERROR_CODE = 400;
    public static final String VERIFICATION_ERROR = "校验异常!";
    public static final int NOT_ALLOWED_CODE = 401;
    public static final String NOT_ALLOWED = "没有权限!";
    public static final int TOKEN_EXPIRE_CODE = 4001;
    public static final String TOKEN_EXPIRE = "凭证已过期，请重新登录!";
    public static final int NOT_FOUND_CODE = 404;
    public static final String NOT_FOUND = "路径不存在!";
    public static final int METHOD_ERROR_CODE = 405;
    public static final String METHOD_ERROR = "不支持该请求方法!";
    public static final int WARNING_CODE = 600;

    /**
     * 分页
     */
    public enum Page {
        CURRENT("current"),
        SIZE("size");

        private String value;

        Page(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
