package org.impc.publications;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
@EnableAutoConfiguration
@PropertySource("file:${user.home}/configfiles/publications/${profile:dev}/application.properties")
public class PublicationsApiConfig {

    static final String SOLR_HOST = "spring.data.solr.host";
    @Resource
    private Environment environment;
}