package com.github.joostlambregts.nestedtimings.api.interfaces;

import java.util.Map;

public interface NestedTiming {
    long getElapsedMillis();
    int getExecutions();
    String getName();
    Map<String,? extends NestedTiming> getChildren();
    NestedTiming getParent();
}
