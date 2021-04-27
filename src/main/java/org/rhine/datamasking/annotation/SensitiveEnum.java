package org.rhine.datamasking.annotation;

/**
 * 敏感数据脱敏方式
 */
public enum SensitiveEnum {

    /**
     * 默认脱敏方式，忽略原字符串长度，使用"***"替换，数字类型用"0"替换，布尔值默认使用"false"替换。
     */
    DEFAULT,

    /**
     * 正则匹配需要脱敏内容。
     */
    REGULAR,

    /**
     * 将手机第四位到第七位全部替换成"*"，比如"18188887777"脱敏之后"181****7777"。
     */
    PHONE,

    /**
     * 除了身份证前四后三全部替换成"*"，比如"513436200004218136"脱敏之后"5134***********136"。
     */
    ID_CARD;


    public String getType() {
        return name();
    }

}
