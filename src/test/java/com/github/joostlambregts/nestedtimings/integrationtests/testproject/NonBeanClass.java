package com.github.joostlambregts.nestedtimings.integrationtests.testproject;

import com.github.joostlambregts.nestedtimings.api.NestedTimingsFacade;

public class NonBeanClass {
    public void methodWithManualTimer(){
        NestedTimingsFacade.startTimedSegment("manualTimedSegment");
        NestedTimingsFacade.endTimedSegment();
    }
}
