package com.github.joostlambregts.nestedtimings.integrationtests.testproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
    @Autowired
    private TestService testService;
    public void controllerMethod(){
        testService.serviceMethodNotAnnotated();
        testService.serviceMethodAnnotatedAsTimed();
    }
}
