package com.github.joostlambregts.nestedtimings.internal.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RepositoryAspect {

    private final AspectUtil aspectUtil;

    public RepositoryAspect(AspectUtil aspectUtil) {
        this.aspectUtil = aspectUtil;
    }

    @Around("within(@org.springframework.stereotype.Repository *)")
    public Object timeController(ProceedingJoinPoint joinPoint) throws Throwable {
        return aspectUtil.doTiming(joinPoint);
    }
}
