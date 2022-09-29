package com.zhen.aspect;

import com.alibaba.fastjson.JSON;
import com.zhen.annotation.InterfaceLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InterfaceAddress;

/**
 *  日志记录切面类
 *
 * @author 甄子函
 * @date: 2022/9/22__21:32
 */
@Component
@Aspect
@Slf4j
public class LogAspect {


    //确定切点
    @Pointcut("@annotation(com.zhen.annotation.InterfaceLog)")
    public void pt(){
    }

    /**
     *
     * @param joinPoint 被增强方法信息，封装成的对象
     * @return
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object ret = null;
        try {
            handleBefore(joinPoint); //方法执行之前调用
            ret = joinPoint.proceed();
            handleAfter(ret);  //方法执行之后调用
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }

        return ret;
    }


    private void handleBefore(ProceedingJoinPoint joinPoint) {
        //获取请求url(从spring封装好的RequestContextHolder中获取) 强转成ServletRequestAttributes获取到对应的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上的注解对象
        InterfaceLog interfacelog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("InterfaceType   : {}", interfacelog.InterfaceType());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature)joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }


    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}",JSON.toJSONString(ret) );

    }

    private InterfaceLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        InterfaceLog interfacelog = methodSignature.getMethod().getAnnotation(InterfaceLog.class);
        return interfacelog;
    }
}
