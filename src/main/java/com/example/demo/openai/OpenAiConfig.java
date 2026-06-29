package com.example.demo.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * 根据外部配置创建 OpenAI 客户端。
 */
@Configuration
@ConditionalOnProperty(prefix = "openai", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(OpenAiProperties.class)
public class OpenAiConfig {

    /**
     * 构建一个可复用的 API 客户端，用于发起模型调用。
     */
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
