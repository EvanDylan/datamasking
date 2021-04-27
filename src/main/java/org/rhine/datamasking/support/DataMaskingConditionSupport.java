package org.rhine.datamasking.support;

import org.rhine.datamasking.core.MaskingContext;
import org.rhine.datamasking.support.spi.DataMaskingConditionSPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DataMaskingConditionSupport {

    private static final Logger logger = LoggerFactory.getLogger(DataMaskingConditionSupport.class);

    private static DataMaskingConditionSPI DATA_MASKING_CONDITION = null;

    static {
        Iterator<DataMaskingConditionSPI> iterator = ServiceLoader.load(DataMaskingConditionSPI.class).iterator();
        try {
            if (iterator.hasNext()) {
                DATA_MASKING_CONDITION = iterator.next();
            }
        } catch (Throwable e) {
            logger.error("load spi implements error", e);
        }
    }

    public static boolean needMasking(MaskingContext context) {
        if (DATA_MASKING_CONDITION != null) {
            return DATA_MASKING_CONDITION.needMasking(context);
        }
        return true;
    }
}
