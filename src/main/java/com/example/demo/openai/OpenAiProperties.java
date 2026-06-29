package com.example.demo.openai;

import jakarta.validation.constraints.NotBlank;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 绑定自配置文件的 OpenAI 相关参数。
 */
@Validated
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {

    /** 用于鉴权的 API Key。 */
    @NotBlank
    private String apiKey;

    /** OpenAI 兼容接口的基础地址。 */
    @NotBlank
    private String baseUrl = "https://api.openai.com/v1";

    /** 当请求未指定模型时使用的默认模型。 */
    @NotBlank
    private String model = "gpt-5.5";

    /** 调用模型接口时使用的全局超时时间。 */
    private Duration timeout = Duration.ofSeconds(60);

    /** 部分服务商支持的 organization 头，可选。 */
    private String organization;

    /** 部分服务商支持的 project 头，可选。 */
    private String project;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
