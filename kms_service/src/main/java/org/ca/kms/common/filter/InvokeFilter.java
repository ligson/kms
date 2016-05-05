package org.ca.kms.common.filter;


import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ligson on 2016/4/27.
 * dubboFilter
 */
public class InvokeFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(InvokeFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        if (result.hasException()) {
            logger.info("业务处理异常!");
        }
        return result;
    }
}
