package org.rhine.datamasking.support.spi;

import org.rhine.datamasking.core.MaskingContext;

/**
 * 拓展接口，由业务方实现是否脱敏的控制
 */
public interface DataMaskingConditionSPI {

    boolean needMasking(MaskingContext context);

}
