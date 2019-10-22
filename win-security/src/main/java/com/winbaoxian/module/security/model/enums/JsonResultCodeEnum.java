package com.winbaoxian.module.security.model.enums;

/**
 * 返回前端code枚举值
 *
 * @Author DongXL
 * @Create 2018-03-14 10:25
 */
public enum JsonResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),

    /**
     * 表示用户没有权限（令牌、用户名、密码错误）
     */
    UNAUTHORIZED(401),
    /**
     * 表示用户得到授权（与401错误相对），但是访问是被禁止的。
     */
    FORBIDDEN(403),
    /**
     * 服务器发生错误，用户将无法判断发出的请求是否成功。
     */
    INTERNAL_SERVER_ERROR(500);

    private Integer value;

    JsonResultCodeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
