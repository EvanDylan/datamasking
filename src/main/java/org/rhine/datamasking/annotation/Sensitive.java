package org.rhine.datamasking.annotation;

import java.lang.annotation.*;

/**
 * 脱敏注解
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {

    /**
     * <p>
     * 指定脱敏的方式：
     * 1. {@link SensitiveEnum#DEFAULT} 忽略原字段长度字符串、数字、布尔都将分别替换成固定的"***"、0、false，<br/>
     * 2. {@link SensitiveEnum#REGULAR} 支持自定义正则表达式，根据匹配字符长度进行替换。
     * 3. {@link SensitiveEnum#PHONE}   默认将手机第四位到第七位全部替换成"*"，比如"18188887777"脱敏之后"181****7777"。
     * 4. {@link SensitiveEnum#ID_CARD} 除了身份证前四后三全部替换成"*"，比如"513436200004218136"脱敏之后"5134***********136"
     * </p>
     * @see SensitiveEnum
     */
    SensitiveEnum type() default SensitiveEnum.DEFAULT;

    /**
     * 当{@link Sensitive#type()}设置为{@link SensitiveEnum#REGULAR}时，可以使用自定义正则表达式匹配需要脱敏的内容。
     * @return 正则表达式
     */
    String regex() default "";

    /**
     * 用于替换的字符，根据实际匹配字符长度进行等量的替换。
     */
    String maskingValue() default "";
}
