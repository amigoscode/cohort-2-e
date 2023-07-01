package com.amigoscode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {


}
