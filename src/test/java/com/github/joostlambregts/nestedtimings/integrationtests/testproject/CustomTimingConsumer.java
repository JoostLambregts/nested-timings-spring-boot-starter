package com.github.joostlambregts.nestedtimings.integrationtests.testproject;

import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTiming;
import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTimingConsumer;

public class CustomTimingConsumer implements NestedTimingConsumer {
    @Override
    public void accept(NestedTiming nestedTiming) {
        throw new CustomTimingConsumerCalledException();


    }
    public static class CustomTimingConsumerCalledException extends RuntimeException{}
}
