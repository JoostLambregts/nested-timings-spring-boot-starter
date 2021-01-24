package com.github.joostlambregts.nestedtimings.internal.aspects;

import com.github.joostlambregts.nestedtimings.internal.TimerManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AspectUtil {
    private final TimerManager timerManager;

    @Autowired
    public AspectUtil(TimerManager timerManager) {
        this.timerManager = timerManager;
    }

    public Object doTiming(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        //check if method is currently being timed to avoid double timings due to overlapping pointcuts
        if (methodName.equals(timerManager.getCurrentTimerName())) return joinPoint.proceed();

        timerManager.startTimedSegment(joinPoint.getSignature().getName());
        Object ret = joinPoint.proceed();
        timerManager.EndTimedSegment();
        return ret;
    }
}
