package org.rhine.datamasking.core;

import org.rhine.datamasking.annotation.Sensitive;
import org.rhine.datamasking.support.DataMaskingConditionSupport;
import org.rhine.datamasking.utils.ReflectUtils;
import org.rhine.datamasking.utils.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class SensitiveDataProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveDataProcessor.class);

    public static void processAnnotationsSensitive(Method matchedMethod, Object returnValue) {
        // skip primitive return type
        if (matchedMethod == null) return;
        Class<?> returnClass = matchedMethod.getReturnType();
        if (returnClass.isPrimitive()) return;

        MaskingContext maskingContext = new MaskingContext();
        maskingContext.setMethod(matchedMethod);
        try {
            doProcessAnnotationsSensitive(maskingContext, returnClass, returnValue);
        } finally {
            maskingContext.getThreadLocalMap().remove();
        }
    }

    public static void doProcessAnnotationsSensitive(MaskingContext maskingContext, Class<?> returnClass, Object value) {
        if (Types.unSupportType(returnClass) && ReflectUtils.annotationWith(returnClass, Sensitive.class)) {
            throw new UnsupportedOperationException("unsupported type " + returnClass.getTypeName());
        }
        if (value == null) return;
        Field[] fields = returnClass.getDeclaredFields();
        if (fields.length == 0) return;
        for (Field field : fields) {
            Class<?> fieldClazzType = field.getType();
            field.setAccessible(true);

            // primitive type
            if (Types.isSupportType(fieldClazzType)) {
                Sensitive[] sensitives = field.getDeclaredAnnotationsByType(Sensitive.class);
                if (sensitives == null || sensitives.length <= 0) continue;
                Sensitive sensitive = sensitives[0];
                try {
                    maskingContext.setField(field);
                    maskingContext.setSensitive(sensitive);
                    maskingContext.setValue(field.get(value));
                    maskingContext.setFieldParameterClazz(fieldClazzType);
                    if (DataMaskingConditionSupport.needMasking(maskingContext)) {
                        field.set(value, DataMaskingHandlerFactory
                                .adaptedDataMasking(sensitive.type()).masking(maskingContext));
                    }
                } catch (IllegalAccessException e) {
                    logger.error("doProcessAnnotationsSensitive error", e);
                }
            }

            // List
            else if (Types.isListType(fieldClazzType)) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                Class<?> parameterClazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                Sensitive[] sensitives = field.getDeclaredAnnotationsByType(Sensitive.class);
                Sensitive sensitive = null;
                if (sensitives != null && sensitives.length > 0) {
                    sensitive = sensitives[0];
                }
                try {
                    @SuppressWarnings("unchecked")
                    List<Object> filedValues = (List<Object>) field.get(value);
                    if (filedValues != null && filedValues.size() > 0) {
                        if (Types.isStringType(parameterClazz) && sensitive != null) {
                            maskingContext.setField(field);
                            maskingContext.setSensitive(sensitive);
                            maskingContext.setFieldParameterClazz(parameterClazz);
                            maskingContext.setValue(filedValues);
                            if (DataMaskingConditionSupport.needMasking(maskingContext)) {
                                Object maskingValue = DataMaskingHandlerFactory
                                        .adaptedDataMasking(sensitive.type()).masking(maskingContext);
                                List<Object> maskingValues = new ArrayList<>();
                                for (Object o : filedValues) {
                                    maskingValues.add(maskingValue);
                                }
                                field.set(value, maskingValues);
                            }
                        } else {
                            for (Object o : filedValues) {
                                doProcessAnnotationsSensitive(maskingContext, parameterClazz, o);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    logger.error("doProcessAnnotationsSensitive error", e);
                }
            }

            // inner object
            else {
                try {
                    Object filedValue = field.get(value);
                    Class<?> fieldType = field.getType();
                    doProcessAnnotationsSensitive(maskingContext, fieldType, filedValue);
                } catch (IllegalAccessException e) {
                    logger.error("doProcessAnnotationsSensitive error", e);
                }
            }
        }
    }
}
