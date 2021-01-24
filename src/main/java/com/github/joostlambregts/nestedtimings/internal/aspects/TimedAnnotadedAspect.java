package com.github.joostlambregts.nestedtimings.internal.aspects;

import com.github.joostlambregts.nestedtimings.internal.TimerManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimedAnnotadedAspect {

    private final AspectUtil aspectUtil;

    @Autowired
    public TimedAnnotadedAspect(TimerManager timerManager, AspectUtil aspectUtil) {
        this.aspectUtil = aspectUtil;
    }

    @Around("@annotation(com.github.joostlambregts.nestedtimings.api.interfaces.Timed)")
    public Object timeAnnotated(ProceedingJoinPoint joinPoint) throws Throwable {
        return aspectUtil.doTiming(joinPoint);
    }
}
