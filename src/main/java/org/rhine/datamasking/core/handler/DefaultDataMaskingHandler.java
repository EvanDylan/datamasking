package org.rhine.datamasking.core.handler;

import org.rhine.datamasking.core.Constants;
import org.rhine.datamasking.core.MaskingContext;
import org.rhine.datamasking.core.UnSupportedException;
import org.rhine.datamasking.utils.Types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;

public class DefaultDataMaskingHandler implements DataMasking {

    @Override
    public Object masking(MaskingContext maskingContext) {
        String maskingValue = maskingContext.getSensitive().maskingValue();
        maskingValue = defaultMaskingValue(maskingContext.getFieldParameterClazz(), maskingValue);
        if (Types.isStringType(maskingContext.getFieldParameterClazz())) {
            return maskingValue;
        }
        else if (Types.isBooleanType(maskingContext.getFieldParameterClazz())) {
            return Boolean.valueOf(maskingValue);
        }
        else if (Types.isBasicDataType(maskingContext.getFieldParameterClazz())) {
            return Byte.valueOf(maskingValue);
        }
        else if (Byte.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new Byte(maskingValue);
        }
        else if (Short.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new Short(maskingValue);
        }
        else if (Integer.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new Integer(maskingValue);
        }
        else if (Long.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new Long(maskingValue);
        }
        else if (Float.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new Float(maskingValue);
        }
        else if (Double.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new Double(maskingValue);
        }
        else if (BigDecimal.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new BigDecimal(maskingValue);
        }
        else if (BigInteger.class.isAssignableFrom(maskingContext.getFieldParameterClazz())) {
            return new BigInteger(maskingValue);
        }
        throw new UnSupportedException("un supported type " + maskingContext.getFieldParameterClazz().getName());
    }

    String defaultMaskingValue(Class<?> fieldClazzType, String specifyValue) {
        if (specifyValue != null && !"".equals(specifyValue)) {
            return specifyValue;
        }
        if (Types.isStringType(fieldClazzType)) {
            return String.join("", Collections.nCopies(3, Constants.DEFAULT_MASKING_STRING));
        }
        if (Types.isBooleanType(fieldClazzType)) {
            return Constants.DEFAULT_MASKING_BOOLEAN;
        }
        if (Types.isBasicDataType(fieldClazzType) || Types.isPackageDataType(fieldClazzType)) {
            return Constants.DEFAULT_MASKING_NUMBER;
        }
        return null;
    }
}
