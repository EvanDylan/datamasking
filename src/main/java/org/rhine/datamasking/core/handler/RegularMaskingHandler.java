package org.rhine.datamasking.core.handler;

import org.rhine.datamasking.core.Constants;
import org.rhine.datamasking.core.MaskingContext;
import org.rhine.datamasking.utils.Types;

import java.util.regex.Pattern;

/**
 * 自定义正则表达式，根据匹配字符长度进行替换。
 */
public class RegularMaskingHandler implements DataMasking {

    @Override
    public Object masking(MaskingContext maskingContext) {
        String regex = maskingContext.getSensitive().regex();
        String maskingValue = maskingContext.getSensitive().maskingValue();
        Pattern pattern = Pattern.compile(regex);
        if (Types.isStringType(maskingContext.getFieldParameterClazz()) && maskingContext.getValue() != null) {
            if ("".equals(maskingValue)) {
                maskingValue = Constants.DEFAULT_MASKING_STRING;
            }
           return pattern.matcher((String) maskingContext.getValue()).replaceAll(maskingValue);
        }
        return maskingContext.getValue();
    }
}
