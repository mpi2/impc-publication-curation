package org.impc.publications;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.impc.publications.repositories.PublicationRepositoryCustomImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;

@Configuration
@EnableAutoConfiguration
@EnableSolrRepositories(basePackages={"org.impc.publications.repositories"})
@PropertySource("file:${user.home}/configfiles/publications/${profile:dev}/application.properties")
public class PublicationsApiConfig {

    static final String SOLR_HOST = "spring.data.solr.host";
    @Resource
    private Environment environment;

    @Bean
    public SolrClient solrClient() {
        String solrHost = environment.getRequiredProperty(SOLR_HOST);
        return new HttpSolrClient(solrHost);
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
}