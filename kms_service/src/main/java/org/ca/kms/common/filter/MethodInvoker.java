package org.ca.kms.common.filter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ligson.fw.core.common.biz.AbstractBiz;
import org.ligson.fw.core.facade.annotation.Api;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.core.facade.enums.FailureCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.annotation.Annotation;

/**
 * Created by ligson on 2016/4/27.
 */
public class MethodInvoker implements MethodInterceptor {
    private static final String PERFIX = "=============>";
    private static Logger logger = LoggerFactory.getLogger(MethodInvoker.class);
    @Autowired
    @Qualifier("mySessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object target = methodInvocation.getThis();
        if (target instanceof AbstractBiz) {
            Class bizClass = target.getClass();
            logger.debug(bizClass.getName());
            Annotation annotation = bizClass.getDeclaredAnnotation(Api.class);
            if (annotation != null) {
                if (annotation instanceof Api) {
                    Api api = (Api) annotation;
                    logger.debug("\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=\\/=");
                    logger.debug("{}开始调用:{}【{}】", PERFIX, api.name(), bizClass.getName());
                    long start = System.currentTimeMillis();
                    Object result;
                    try {
                        Object[] objects = methodInvocation.getArguments();
                        if (objects.length > 0 && objects[0] instanceof BaseRequestDto) {
                            logger.debug("{} {}请求参数:{}", PERFIX, api.name(), objects[0].toString());
                        }
                        result = methodInvocation.proceed();
                        logger.debug("{} {}响应参数:{}", PERFIX, api.name(), result.toString());
                    } catch (Exception e) {
                        logger.error(PERFIX + api.name() + "调用异常:{}", e);
                        result = Result.getFailureResult(FailureCodeEnum.SERVICE_EXCEPTION.getCode(),
                                FailureCodeEnum.SERVICE_EXCEPTION.getMsg() + ";异常信息:" + e);
                        //事物回滚
                        //事物回滚
                        try {
                            Session session = sessionFactory.getCurrentSession();
                            if (session != null) {
                                session.getTransaction().rollback();
                            }
                        } catch (HibernateException e1) {
                            logger.warn("session transaction exception:{}", e1.getMessage());
                        }
                    } finally {
                        long end = System.currentTimeMillis();
                        logger.debug("{}调用完成:{}【{}】，耗时{}ms", PERFIX, api.name(), bizClass.getName(), end - start);
                        logger.debug("/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=/\\=");
                        try{
                            Session session = sessionFactory.getCurrentSession();
                            if (session != null) {
                                session.close();
                            }
                        }catch(HibernateException e){
                            logger.warn("session close exception:{}",e.getMessage());
                        }
                    }
                    return result;
                }
            }
        }
        return methodInvocation.proceed();
    }
}
