package lk.project.marketing.base.enums;

/**
 * alexlu
 * 2开头是操作成功代码
 * 3开头是认证失败代码 30100以内是保留数字错误码，用户误自定义
 * 4开头是系统相关错误 40100以内是保留数字错误码，用户误自定义
 */
public enum SystemErrorCodeEnum {
    SUCCESS(20000, "操作成功!"),
    UN_PERMISSION(30005, "无权访问!"),
    SYSTEM_ERROR(40000, "系统错误!"),
    INTERFACE_ERROR(40001,"接口调用错误!"),
    DATABASE_ERROR(40002,"数据库连接错误!"),
    IO_ERROR(40003,"IO错误!"),
    ILLEGAL_PARAMETER(40004,"参数错误!"),
    DATEBASE_ERROR(40005,"数据入库失败!"),
    API_ERROR(40006,"API调用失败!"),
    EXCEPTION(40007,"系统异常!"),
    GET_INSTANCE_ERROR(40008, "获取实例失败!");

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;


    /**
     * 使用错误码和错误信息构造枚举
     *
     * @param code    错误码
     * @param message 错误信息
     */
    SystemErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message != null ? message : "";
    }

    /**
     * 获取错误码
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取错误信息
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * 根据Code获取枚举对象
     * @param code
     * @return
     */
    public static SystemErrorCodeEnum getValueByCode(Integer code){
        for(SystemErrorCodeEnum systemErrorCodeEnum : SystemErrorCodeEnum.values()){
            if(systemErrorCodeEnum.getCode().equals(code)){
                return systemErrorCodeEnum;
            }
        }

        return null;
    }
}
