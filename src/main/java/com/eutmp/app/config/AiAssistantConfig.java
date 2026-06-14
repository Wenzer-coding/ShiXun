package com.eutmp.app.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AiAssistantConfig {

    private final AiAssistantProperties properties;

    @Bean
    public ChatModel chatModel() {
        if (!properties.isEnabled()) {
            log.warn("AI 助手已禁用");
            return null;
        }

        log.info("初始化 AI 助手模型: {} (baseUrl: {})", properties.getModelName(), properties.getBaseUrl());

        return OpenAiChatModel.builder()
                .baseUrl(properties.getBaseUrl())
                .modelName(properties.getModelName())
                .apiKey(properties.getApiKey())
                .temperature(properties.getTemperature())
                .maxTokens(properties.getMaxTokens())
                .timeout(Duration.ofSeconds(properties.getTimeout()))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
