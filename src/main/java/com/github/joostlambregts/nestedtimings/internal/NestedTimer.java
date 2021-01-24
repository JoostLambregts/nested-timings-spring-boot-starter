package com.github.joostlambregts.nestedtimings.internal;

import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTiming;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

public class NestedTimer implements NestedTiming {
    private Instant startTime;
    private int executions = 0;
    private long elapsedMillis = 0;
    private final NestedTimer parent;
    private final Map<String, NestedTimer> children = new LinkedHashMap<>();
    private final String name;

    @Override
    public long getElapsedMillis() {
        return elapsedMillis;
    }

    @Override
    public int getExecutions() {
        return executions;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<String, NestedTimer> getChildren() {
        return children;
    }

    @Override
    public NestedTiming getParent() {
        return parent;
    }

    protected void start(){
        startTime = Instant.now();
    }

    NestedTimer(String name, NestedTimer parent) {
        this.startTime = Instant.now();
        this.parent = parent;
        this.name = name;
    }

    void end() {
        Instant currentTime = Instant.now();
        elapsedMillis += ChronoUnit.MILLIS.between(startTime, currentTime);
        startTime = null;
        executions += 1;
    }
}