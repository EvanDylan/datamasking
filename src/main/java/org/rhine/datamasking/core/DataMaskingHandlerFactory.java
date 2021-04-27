package org.rhine.datamasking.core;


import org.rhine.datamasking.annotation.SensitiveEnum;
import org.rhine.datamasking.core.handler.*;

public class DataMaskingHandlerFactory {

    public static DataMasking adaptedDataMasking(SensitiveEnum sensitiveEnum) {
        if (SensitiveEnum.DEFAULT == sensitiveEnum) {
            return new DefaultDataMaskingHandler();
        } else if (SensitiveEnum.REGULAR == sensitiveEnum) {
            return new RegularMaskingHandler();
        } else if (SensitiveEnum.PHONE == sensitiveEnum) {
            return new PhoneMaskingHandler();
        } else if (SensitiveEnum.ID_CARD == sensitiveEnum) {
            return new IdCardMaskingHandler();
        }
        throw new UnsupportedOperationException();
    }
}
