package com.github.joostlambregts.nestedtimings;

import com.github.joostlambregts.nestedtimings.integrationtests.testproject.CustomTimingConsumer;
import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTimingConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "nl.lambregts.spring.executiontimer.api","nl.lambregts.spring.executiontimer.internal","nl.lambregts.spring.executiontimer.integrationtests.testproject","nl.lambregts.spring.executiontimer.integrationtests.customlogger" })
public class TestConfigurationWithCustomTimingConsumer {
    @Bean
    public NestedTimingConsumer customTimingConsumer(){
        return new CustomTimingConsumer();
    }
}
