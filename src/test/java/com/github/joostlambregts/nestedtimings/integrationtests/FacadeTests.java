package com.github.joostlambregts.nestedtimings.integrationtests;

import com.github.joostlambregts.nestedtimings.integrationtests.testproject.ComponentTimedThroughCustomAspect;
import com.github.joostlambregts.nestedtimings.integrationtests.testproject.NonBeanClass;
import com.github.joostlambregts.nestedtimings.Configuration;
import com.github.joostlambregts.nestedtimings.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.github.joostlambregts.nestedtimings.TestUtil.checkLogging;

@SpringBootTest(classes = Configuration.class)
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
public class FacadeTests {

    @BeforeEach
    void setup(){
        TestUtil.setup();
    }

    @AfterEach
    void tearDown(){
        TestUtil.teardown();
    }

    @Autowired
    ComponentTimedThroughCustomAspect componentTimedThroughCustomAspect;

    @Test
    void testCustomAspect(){
        Instant start = Instant.now();
        componentTimedThroughCustomAspect.methodTimedThroughCustomAspect();
        long elapsed = ChronoUnit.MILLIS.between(start, Instant.now());
        List<TestUtil.LogLineSpec> specs = new ArrayList<>();
        specs.add(new TestUtil.LogLineSpec(0,1,elapsed,"methodTimedThroughCustomAspect"));
        checkLogging(specs,0,false);
    }

    @Test
    void testManualTimedSegment(){
        NonBeanClass nonBeanClass = new NonBeanClass();
        Instant start = Instant.now();
        nonBeanClass.methodWithManualTimer();
        long elapsed = ChronoUnit.MILLIS.between(start, Instant.now());
        List<TestUtil.LogLineSpec> specs = new ArrayList<>();
        specs.add(new TestUtil.LogLineSpec(0,1,elapsed,"manualTimedSegment"));
        checkLogging(specs,0,false);
    }
}
