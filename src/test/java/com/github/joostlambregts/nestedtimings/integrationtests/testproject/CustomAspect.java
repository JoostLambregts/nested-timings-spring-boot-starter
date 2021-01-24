package com.github.joostlambregts.nestedtimings.integrationtests.testproject;

import com.github.joostlambregts.nestedtimings.api.NestedTimingsFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomAspect {
    @Around("execution(* com.github.joostlambregts.nestedtimings.integrationtests.testproject.ComponentTimedThroughCustomAspect.*(..))")
    public Object doTiming(ProceedingJoinPoint joinPoint) throws Throwable {
        return NestedTimingsFacade.TimeJoinpoint(joinPoint);
    }
}
