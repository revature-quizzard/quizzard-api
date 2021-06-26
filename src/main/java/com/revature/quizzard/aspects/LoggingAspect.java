package com.revature.quizzard.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
/**
 * @author Nicholas Recino
 * Logging aspect to invoke around all point cuts
 */
public class LoggingAspect {

    Logger logger = LogManager.getLogger();

    @Pointcut("within(com.revature.quizzard..*) && !within(com.revature.quizzard.filters..*)")
    public void logAll() {
        // Empty due to pointcuts needing to have empty logic within a method.
    }

    @Before("logAll()")
    public void logMethodStart(JoinPoint jp) {
        String methodSig = extractMethodSignature(jp);
        String argStr = Arrays.toString(jp.getArgs());
        logger.info("{} invoked at {}", methodSig, LocalDateTime.now());
        logger.info("Input arguments: {}", argStr);
    }

    @AfterReturning(pointcut = "logAll()", returning = "returnedObj")
    public void logMethodReturn(JoinPoint jp, Object returnedObj) {
        String methodSig = extractMethodSignature(jp);
        logger.info("{} successfully returned at {}", methodSig, LocalDateTime.now());
        logger.info("Object returned: {}", returnedObj);
    }

    @AfterThrowing(pointcut = "logAll()", throwing = "e")
    public void logMethodException(JoinPoint jp, Throwable e) {
        String methodSig = extractMethodSignature(jp);
        logger.warn("{} was thrown in method {} at {} with message: {}", e.getClass().getSimpleName(), methodSig, LocalDateTime.now(), e.getMessage());
    }

    private String extractMethodSignature(JoinPoint jp) {
        return jp.getTarget().getClass().toString() + "." + jp.getSignature().getName();
    }

}
