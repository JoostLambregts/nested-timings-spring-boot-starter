package com.github.joostlambregts.nestedtimings.api;

import com.github.joostlambregts.nestedtimings.internal.TimerManager;
import com.github.joostlambregts.nestedtimings.internal.aspects.AspectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NestedTimingsFacade {
    private static TimerManager timerManager;
    private static AspectUtil aspectUtil;
    private static boolean enabled = false;

    @Autowired
    public NestedTimingsFacade(TimerManager timerManager, AspectUtil aspectUtil) {
        NestedTimingsFacade.timerManager = timerManager;
        NestedTimingsFacade.aspectUtil = aspectUtil;
        //beans are instantiated if and only if l.lambregts.executiontimer.enabled=true.
        if (aspectUtil != null && timerManager != null){
            enabled = true;
        }
    }

    public static void startTimedSegment(String name){
        if (!enabled) return;
        timerManager.startTimedSegment(name);
    }

    public static void endTimedSegment(){
        if (!enabled) return;
        timerManager.EndTimedSegment();
    }

    public static Object TimeJoinpoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!enabled) return joinPoint.proceed();
        return aspectUtil.doTiming(joinPoint);
    }


}
