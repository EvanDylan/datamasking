package org.rhine.datamasking.core.handler;

import org.rhine.datamasking.core.Constants;
import org.rhine.datamasking.core.MaskingContext;
import org.rhine.datamasking.utils.Types;

import java.util.Collections;

/**
 * 除了身份证前四后三全部替换成"*"，比如"513436200004218136"脱敏之后"5134***********136"
 */
public class IdCardMaskingHandler implements DataMasking {

    @Override
    public Object masking(MaskingContext maskingContext) {
        if (Types.isStringType(maskingContext.getFieldParameterClazz()) && maskingContext.getValue() != null) {
            String idChard = (String) maskingContext.getValue();
            int length = idChard.length();
            if (length >= 15) {
                String maskingValue = maskingContext.getSensitive().maskingValue();
                if ("".equals(maskingValue)) {
                    maskingValue = Constants.DEFAULT_MASKING_STRING;
                }
                return idChard.substring(0, 4)
                        + String.join("", Collections.nCopies(length - 7, maskingValue))
                        + idChard.substring(length - 3, length);
            }
        }
        return maskingContext.getValue();
    }
}
