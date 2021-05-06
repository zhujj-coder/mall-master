package com.macro.mall.common.enums;

/**
 * @author KingDow
 */

public enum ExceptionEnum {
    /**
     * UNKNOWN_ERROR 未知错误
     * NO_RESULT 不存在结果
     */
    // 成功 200
    SUCCESS(200, "操作成功"),

    // 未知错误 -1
    UNKNOWN_ERROR(-1, "未知错误"),
    PARAMS_LOST(400001, "参数校验失败"),
    NO_RESULT(400002, "不存在结果"),

    // 增删改查 4001xx
    INSERT_ERROR(400100, "新增操作失败"),
    DELETE_ERROR(400101, "删除操作失败"),
    UPDATE_ERROR(400102, "更新操作失败"),
    SELECT_ERROR(400103, "查询操作失败"),

    // 设备错误 4002xx
    NO_ANDROID_DEVICE(400200, "无可用android设备"),
    NOT_SUPPORT_OS(400201, "不支持的操作系统"),

    // 数据库错误 4003xx
    RECORD_EXIST(400300, "当前记录已存在，请勿重复添加"),
    ADD_DEVICE_ERROR(400301, "新增设备信息失败"),

    // 网络异常 4004xx
    TIME_OUT(400400, "请求超时"),

    // 运行错误 4005xx
    TEST_CASE_RUN_ERROR(400500, "测试用例运行失败"),
    TEST_SUITE_RUN_ERROR(400501, "测试用例集运行失败"),

    // 上传错误 4006xx
    IMG_EMPTY_ERROR(400600, "上传图片不能为空"),
    IMG_FORMAT_ERROR(400601, "所上传的图片格式错误"),
    IMG_SAVE_ERROR(400602, "上传图片保存报错"),
    IMG_UPLOAD_ERROR(400603, "上传图片错误"),
    FILE_UPLOAD_ERROR(400604, "上传文件错误"),
    FILE_NOT_EXISTS(400605,"上传失败，请选择文件"),
    FILE_RUN_CASE_NOT_EXISTS(400606,"检查脚本是否存在runcase目录或联系值班人员"),

    // 用户名密码错误 4007xx
    LOGIN_INFO_ERROR(400700, "用户名或密码错误"),
    LOGIN_NAME_ERROR(400701, "登录用户名信息错误"),
    LOGIN_PASSWORD_ERROR(400702, "用户登录密码信息错误"),
    DOUBLE_REGISTER_ERROR(400703, "已存在的用户，请勿重复注册操作"),

    // token 相关错误 4008xx
    NEW_TOKEN_ERROR(400801, "生成token失败"),
    TOKEN_VALIDATE_ERROR(400802, "无效的token"),
    // 打包相关
    APK_ERROR(40090,"打包失败！"),
    APK_URL_ERROR(40091,"返回成功，地址无效！"),
    LOG_ANA_URL_ERROR(40092,"解析返回编译的错误日志失败！"),
//    构建相关
    BUILD_TIME_ERROR(40100,"人工触发构建时间段不允许在00：00-7:00之间"),
    BUILD_OTHER_ERROR(40101,"上个流程尚未结束，请稍后再试（云真机可看运行情况）！"),

//    短信相关
    MOBILE_SEND_FRE(40201,"短信发送过于频繁！"),
    MOBILE_CHECK_FRE(40202,"短信校验过于频繁！"),
    MOBILE_SEND_IP_NULL(40203,"短信发送失败【ip异常】！"),
    MOBILE_WRONG(40204,"验证码有误！"),
//    退货
    RETURN_REPEATED(40300,"请勿重复退款！"),
//    调用打印机
    PRINTER_ERROR(40400,"调用打印机异常！"),
    PRINTER_TYPE_ERROR(40401,"打印机类型有误！"),
    //    调用打印机
    WX_ERROR(40500,"调用微信异常！"),
    FLASH_BUY_LIMIT(40600,"限购数量达到上限！"),
    FLASH_BUY_FINISH(40601,"您已秒杀过！"),
    FLASH_STOCK_EMPTY(40602,"库存不足，已被抢光！"),
    TAKE_CODE_ERROR(40700,"取货码长度必须为4！");
    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMessage(int code) {
        for (ExceptionEnum c : ExceptionEnum.values()) {
            if (c.getCode() == code) {
                return c.msg;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
