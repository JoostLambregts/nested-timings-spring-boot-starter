package com.github.joostlambregts.nestedtimings.integrationtests.testproject;

import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTimingConsumer;
import org.springframework.context.annotation.Bean;

public class CustomTimerConfiguration {
    @Bean
    NestedTimingConsumer nestedTimingConsumer(){
        return new CustomTimingConsumer();
    }
}
