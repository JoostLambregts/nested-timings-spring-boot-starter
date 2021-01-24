package com.github.joostlambregts.nestedtimings.integrationtests;

import com.github.joostlambregts.nestedtimings.integrationtests.testproject.TestController;
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

import com.github.joostlambregts.nestedtimings.TestUtil.LogLineSpec;
import static com.github.joostlambregts.nestedtimings.TestUtil.*;
@SpringBootTest(classes = {Configuration.class})
@Import(AnnotationAwareAspectJAutoProxyCreator.class)

public class AspectsTestDefaultProperties {
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
    void whenDefaultPropertiesThenAllMethodsLogged(){
        Instant start = Instant.now();
        testController.controllerMethod();
        long elapsed = ChronoUnit.MILLIS.between(start, Instant.now());
        List<LogLineSpec> specs = new ArrayList<>();
        specs.add(new LogLineSpec(0,1,elapsed,"controllerMethod"));
        specs.add(new LogLineSpec(1,1,0,"serviceMethodNotAnnotated"));
        specs.add(new LogLineSpec(1,1,0,"serviceMethodAnnotatedAsTimed"));
        specs.add(new LogLineSpec(2,1,0,"repositoryMethod"));
        checkLogging(specs,0,false);
    }
}
