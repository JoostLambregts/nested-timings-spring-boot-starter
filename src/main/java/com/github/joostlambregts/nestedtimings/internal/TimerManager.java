package com.github.joostlambregts.nestedtimings.internal;

import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTiming;
import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTimingConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

@Component
public class TimerManager {
    private final Consumer<NestedTiming> timingLogger;
    private final ThreadLocal<NestedTimer> rootTimer = new ThreadLocal<>();
    private final ThreadLocal<NestedTimer> currentTimer = new ThreadLocal<>();

    @Autowired
    public TimerManager(NestedTimingConsumer timingLogger) {
        this.timingLogger = timingLogger;
    }

    public void startTimedSegment(@NotNull String name) {
        NestedTimer startedTimer;
        if (rootTimer.get() == null) {
            startedTimer = new NestedTimer(name, null);
            rootTimer.set(startedTimer);
        } else {
            if (currentTimer.get().getChildren().containsKey(name)) {
                startedTimer = currentTimer.get().getChildren().get(name);
                startedTimer.start();
            } else {
                startedTimer = new NestedTimer(name, currentTimer.get());
                currentTimer.get().getChildren().put(name, startedTimer);
            }
        }
        currentTimer.set(startedTimer);
    }

    public void EndTimedSegment(){
        NestedTimer endedTimer = currentTimer.get();
        endedTimer.end();

        if (endedTimer.getParent() != null) {
            currentTimer.set((NestedTimer)(endedTimer.getParent()));
        } else {
            timingLogger.accept(endedTimer);
            rootTimer.remove();
            currentTimer.remove();
        }
    }

    public String getCurrentTimerName(){
        return currentTimer.get() != null?currentTimer.get().getName():null;
    }
}
