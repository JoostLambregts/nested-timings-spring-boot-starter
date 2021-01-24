package com.github.joostlambregts.nestedtimings.internal.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ControllerRestControllerAspect {

    private final AspectUtil aspectUtil;

    public ControllerRestControllerAspect(AspectUtil aspectUtil) {
        this.aspectUtil = aspectUtil;
    }

    @Around("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    public Object timeController(ProceedingJoinPoint joinPoint) throws Throwable {
        return aspectUtil.doTiming(joinPoint);
    }
}
