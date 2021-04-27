package org.rhine.datamasking.utils;

import java.lang.reflect.Method;

public class ReflectUtils {

    @SuppressWarnings("unchecked")
    public static boolean annotationWith(Class<?> clazz, Class annotation) {
        return clazz.isAnnotation() && clazz.getAnnotation(annotation) != null;
    }

    @SuppressWarnings("unchecked")
    public static boolean annotationWith(Method method, Class annotation) {
        return method.isAnnotationPresent(annotation);
    }

    public static Method findMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        return clazz.getMethod(methodName, parameterTypes);
    }

}
