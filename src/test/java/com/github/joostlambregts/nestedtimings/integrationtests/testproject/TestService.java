package com.github.joostlambregts.nestedtimings.integrationtests.testproject;

import com.github.joostlambregts.nestedtimings.api.interfaces.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    public void serviceMethodNotAnnotated()  {}

    @Timed
    public void serviceMethodAnnotatedAsTimed(){
        testRepository.repositoryMethod();
    }
}
