package com.example.bluejayday;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Define a RestTemplate bean so it can be injected into controllers or services
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
