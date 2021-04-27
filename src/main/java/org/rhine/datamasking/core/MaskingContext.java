package org.rhine.datamasking.core;

import org.rhine.datamasking.annotation.Sensitive;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class MaskingContext {

    /**
     * 传递线程上下文信息
     */
    ThreadLocal<Map<Object, Object>> threadLocalWithMap = new ThreadLocal<>();

    /**
     * 当前执行脱敏的方式
     */
    private Method method;

    /**
     * 当前执行脱敏的字段
     */
    private Field field;

    /**
     * 当前执行脱敏字段的注解
     */
    private Sensitive sensitive;

    /**
     * 当前脱敏字段的值
     */
    private Object value;

    /**
     * 当前脱敏字段类型
     */
    private Class<?> fieldParameterClazz;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Sensitive getSensitive() {
        return sensitive;
    }

    public void setSensitive(Sensitive sensitive) {
        this.sensitive = sensitive;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class<?> getFieldParameterClazz() {
        return fieldParameterClazz;
    }

    public void setFieldParameterClazz(Class<?> fieldParameterClazz) {
        this.fieldParameterClazz = fieldParameterClazz;
    }

    public ThreadLocal<Map<Object, Object>> getThreadLocalMap() {
        return threadLocalWithMap;
    }

}
