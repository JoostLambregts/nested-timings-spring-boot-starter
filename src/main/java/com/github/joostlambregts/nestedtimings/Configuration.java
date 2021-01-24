package com.github.joostlambregts.nestedtimings;

import com.github.joostlambregts.nestedtimings.api.interfaces.NestedTimingConsumer;
import com.github.joostlambregts.nestedtimings.internal.TimingLogger;
import com.github.joostlambregts.nestedtimings.internal.aspects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
@ConditionalOnProperty(prefix = "nestedtimings", name = "enabled", havingValue = "true")
@ComponentScan
public class Configuration {
    @Autowired
    AspectUtil aspectUtil;

    @Bean
    @ConditionalOnMissingBean(NestedTimingConsumer.class)
    public NestedTimingConsumer nestedTimingConsumer(){
        return new TimingLogger();
    }

    //We create either ControllerAspect or ControllerRestControllerAspect based on whether or not RestController is on the classpath.
    //We do this because ControllerRestControllerAspect will fail when RestController is not on classpath.
    @Bean
    @ConditionalOnProperty(prefix = "nestedtimings", name = "includecontrollers",matchIfMissing = true)
    @ConditionalOnMissingClass("org.springframework.web.bind.annotation.RestController")
    public ControllerAspect controllerAspect() {return new ControllerAspect(aspectUtil);}

    @Bean
    @ConditionalOnProperty(prefix = "nestedtimings", name = "includecontrollers",matchIfMissing = true)
    @ConditionalOnMissingBean(ControllerAspect.class)
    public ControllerRestControllerAspect controllerAspectIncludingRest() {return new ControllerRestControllerAspect(aspectUtil);}

    @Bean
    @ConditionalOnProperty(prefix = "nestedtimings", name = "includeservices",matchIfMissing = true)
    public ServiceAspect serviceAspect() {return new ServiceAspect(aspectUtil);}

    @Bean
    @ConditionalOnProperty(prefix = "nestedtimings", name = "includerepositories",matchIfMissing = true)
    public RepositoryAspect repositoryAspect() {return new RepositoryAspect(aspectUtil);}


}
