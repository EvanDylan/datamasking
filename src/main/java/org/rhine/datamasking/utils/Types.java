package org.rhine.datamasking.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Types {

    public static boolean isPackageDataType(Class<?> clazz) {
        return Byte.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)
                || Float.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)
                || BigDecimal.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz);
    }

    public static boolean isBasicDataType(Class<?> clazz) {
        return byte.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)
                || float.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)
                || boolean.class.isAssignableFrom(clazz);
    }

    public static boolean isBooleanType(Class<?> clazz) {
        return boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz);
    }

    public static boolean isStringType(Field field) {
        if (field != null) {
            return String.class.isAssignableFrom(field.getType());
        }
        return false;
    }

    public static boolean isStringType(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    public static boolean isListType(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    public static boolean isSupportType(Class<?> clazz) {
        return isBasicDataType(clazz) || isStringType(clazz) || isPackageDataType(clazz) || isBooleanType(clazz);
    }

    public static boolean unSupportType(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }
}
