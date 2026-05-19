package com.example.demo.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(OpenAiProperties.class)
public class OpenAiConfig {

    @Bean
    OpenAIClient openAIClient(OpenAiProperties properties) {
        OpenAIOkHttpClient.Builder builder = OpenAIOkHttpClient.builder()
                .apiKey(properties.getApiKey())
                .baseUrl(properties.getBaseUrl())
                .timeout(properties.getTimeout());

        if (StringUtils.hasText(properties.getOrganization())) {
            builder.organization(properties.getOrganization());
        }

        if (StringUtils.hasText(properties.getProject())) {
            builder.project(properties.getProject());
        }

        return builder.build();
    }
}
