package com.github.joostlambregts.nestedtimings.integrationtests;

import com.github.joostlambregts.nestedtimings.integrationtests.testproject.ComponentTimedThroughCustomAspect;
import com.github.joostlambregts.nestedtimings.integrationtests.testproject.TestController;
import com.github.joostlambregts.nestedtimings.integrationtests.testproject.TestProjectConfiguration;
import com.github.joostlambregts.nestedtimings.Configuration;
import com.github.joostlambregts.nestedtimings.TestUtil;
import com.github.joostlambregts.nestedtimings.TestUtil.LogLineSpec;
import com.github.joostlambregts.nestedtimings.api.NestedTimingsFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.github.joostlambregts.nestedtimings.TestUtil.checkLogging;

@SpringBootTest(classes = {Configuration.class, TestProjectConfiguration.class}, properties = {"nestedtimings.enabled=false"})
@Import(AnnotationAwareAspectJAutoProxyCreator.class)

public class EnabledFalseTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    TestController testController;
    @Autowired
    ComponentTimedThroughCustomAspect componentTimedThroughCustomAspect;
    @BeforeEach
    void setup(){
        TestUtil.setup();
    }

    @AfterEach
    void tearDown(){
        TestUtil.teardown();
    }
    @Test
    void testWhenDisabledThenNoLoglines(){
        testController.controllerMethod();
        componentTimedThroughCustomAspect.methodTimedThroughCustomAspect();
        NestedTimingsFacade.startTimedSegment("test");
        NestedTimingsFacade.endTimedSegment();
        List<LogLineSpec> specs = new ArrayList<>();
        checkLogging(specs,0,false);
        assertThat("timerManager bean exists",!applicationContext.containsBean("timerManager"));
        assertThat("executionTimerAspect bean exists",!applicationContext.containsBean("executionTimerAspect"));
        assertThat("aspectUtil bean exists",!applicationContext.containsBean("aspectUtil"));
        assertThat("timingLogger bean exists",!applicationContext.containsBean("timingLogger"));
        assertThat("executionTimerFacade bean exists",!applicationContext.containsBean("executionTimerFacade"));
    }
}
