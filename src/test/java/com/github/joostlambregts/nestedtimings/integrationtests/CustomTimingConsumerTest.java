package com.github.joostlambregts.nestedtimings.integrationtests;

import com.github.joostlambregts.nestedtimings.integrationtests.testproject.CustomTimerConfiguration;
import com.github.joostlambregts.nestedtimings.integrationtests.testproject.CustomTimingConsumer;
import com.github.joostlambregts.nestedtimings.integrationtests.testproject.TestController;
import com.github.joostlambregts.nestedtimings.Configuration;
import com.github.joostlambregts.nestedtimings.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = {CustomTimerConfiguration.class, Configuration.class})
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
public class CustomTimingConsumerTest {
    @Autowired
    TestController testController;
    @BeforeEach
    void setup(){
        TestUtil.setup();
    }

    @AfterEach
    void tearDown(){
        TestUtil.teardown();
    }
    @Test
    void exceptionFromCustomTimingConsumerThrown(){
        Assertions.assertThrows(CustomTimingConsumer.CustomTimingConsumerCalledException.class,()->{
            testController.controllerMethod();
        });
    }
}
