package com.management.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import com.management.controller.PersonController;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(PersonController.class);
    }
}
