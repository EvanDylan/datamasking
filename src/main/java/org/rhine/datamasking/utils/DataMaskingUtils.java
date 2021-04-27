package org.rhine.datamasking.utils;

import org.rhine.datamasking.core.MaskingContext;
import org.rhine.datamasking.core.SensitiveDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 数据脱敏工具类
 */
public class DataMaskingUtils {

    private static Logger logger = LoggerFactory.getLogger(DataMaskingUtils.class);

    public static void doMasking(Class<?> clazz, Object value) {
        doMasking(clazz, value, null);
    }

    public static void doMasking(Class<?> clazz, Object value, MaskingContext maskingContext) {
        if (clazz == null || value == null) return;
        if (maskingContext == null) maskingContext = new MaskingContext();
        try {
            SensitiveDataProcessor.doProcessAnnotationsSensitive(maskingContext, clazz, value);
        } catch (Exception e) {
            logger.error("doMasking error", e);
        } finally {
            maskingContext.getThreadLocalMap().remove();
        }
    }

    public static <T> void doMasking(Class<T> clazz, List<T> value) {
        doMasking(clazz, value);
    }

    public static <T> void doMasking(Class<T> clazz, List<T> value, MaskingContext maskingContext) {
        if (value == null || value.isEmpty()) return;
        if (maskingContext == null) maskingContext = new MaskingContext();
        try {
            for (Object o : value) {
                SensitiveDataProcessor.doProcessAnnotationsSensitive(maskingContext, clazz, o);
            }
        } catch (Exception e) {
            logger.error("doMasking error", e);
        } finally {
            maskingContext.getThreadLocalMap().remove();
        }
    }
}
