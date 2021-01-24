package com.github.joostlambregts.nestedtimings.integrationtests;

import com.github.joostlambregts.nestedtimings.integrationtests.testproject.TestController;
import com.github.joostlambregts.nestedtimings.Configuration;
import com.github.joostlambregts.nestedtimings.TestUtil;
import com.github.joostlambregts.nestedtimings.TestUtil.LogLineSpec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static com.github.joostlambregts.nestedtimings.TestUtil.checkLogging;

@SpringBootTest(classes = Configuration.class, properties = {"nestedtimings.includecontrollers=false","nestedtimings.includeservices=false","nestedtimings.includerepositories=false"})
@Import(AnnotationAwareAspectJAutoProxyCreator.class)

public class AspectsTestReversedDefaults {
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
    void whenReverseDefaultsThenOnlyAnnotatedMethodsLogged(){
        testController.controllerMethod();
        List<LogLineSpec> specs = new ArrayList<>();
        specs.add(new LogLineSpec(0,1,0,"serviceMethodAnnotatedAsTimed"));
        checkLogging(specs,0,false);
    }
}
