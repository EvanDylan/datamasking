package org.rhine.datamasking.core.handler;


import org.rhine.datamasking.core.Constants;
import org.rhine.datamasking.core.MaskingContext;
import org.rhine.datamasking.utils.Types;

import java.util.Collections;

/**
 * 手机号按照前三后四规则脱敏
 */
public class PhoneMaskingHandler implements DataMasking {

    @Override
    public Object masking(MaskingContext maskingContext) {
        if (Types.isStringType(maskingContext.getFieldParameterClazz()) && maskingContext.getValue() != null) {
            String phoneNum = (String) maskingContext.getValue();
            if (phoneNum.length() == 11) {
                String maskingValue = maskingContext.getSensitive().maskingValue();
                if ("".equals(maskingValue)) {
                    maskingValue = Constants.DEFAULT_MASKING_STRING;
                }
                return phoneNum.substring(0, 3)
                        + String.join("", Collections.nCopies(4, maskingValue))
                        + phoneNum.substring(7, 11);
            }
        }
        return maskingContext.getValue();
    }
}
