package com.eutmp.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ai.assistant")
public class AiAssistantProperties {
    private boolean enabled = true;
    private String baseUrl = "http://localhost:11434/v1";
    private String modelName = "qwen2:7b";
    private String apiKey = "not-needed";
    private double temperature = 0.7;
    private int maxTokens = 2048;
    private int timeout = 120;
}
