package org.rhine.datamasking.plugins;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.rhine.datamasking.annotation.Sensitive;
import org.rhine.datamasking.core.SensitiveDataProcessor;
import org.rhine.datamasking.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Activate(group = {Constants.PROVIDER}, after = "generic")
public class AlibabaDubboSensitiveFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(AlibabaDubboSensitiveFilter.class);

    private static Map<String, Boolean> SIGNATURE_METHOD_NEED_MASKING = new ConcurrentHashMap<>();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder
                .append(invoker.getInterface().getName())
                .append(".")
                .append(invocation.getMethodName());
        Class<?>[] parameterTypes = invocation.getParameterTypes();
        if (parameterTypes != null && parameterTypes.length > 0) {
            for (Class<?> parameterType : parameterTypes) {
                signatureBuilder.append(parameterType.getName());
            }
        }

        // fast return
        String signature = signatureBuilder.toString();
        Boolean needMasking = SIGNATURE_METHOD_NEED_MASKING.get(signature);
        Result result = invoker.invoke(invocation);
        if (needMasking != null && !needMasking) {
            return result;
        }

        // flag method need data masking or not in cache
        Method matchedMethod = null;
        try {
            matchedMethod = ReflectUtils.findMethod(invoker.getInterface(), invocation.getMethodName(), invocation.getParameterTypes());
            Sensitive sensitive = matchedMethod.getDeclaredAnnotation(Sensitive.class);
            needMasking = sensitive != null;
        } catch (NoSuchMethodException e) {
            // ignore it
            needMasking = false;
        }
        SIGNATURE_METHOD_NEED_MASKING.put(signature, needMasking);

        // masking
        if (result != null && !result.hasException() && result.getValue() != null) {
            if (needMasking) {
                long start = System.currentTimeMillis();
                SensitiveDataProcessor.processAnnotationsSensitive(matchedMethod, result.getValue());
                logger.debug("cost {}ms", (System.currentTimeMillis() - start));
            }
        }
        return result;
    }
}
