package com.eutmp.app.service;

import com.eutmp.app.config.AiAssistantProperties;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiAssistantService {

    private final ChatModel chatModel;
    private final SystemDataTools systemDataTools;
    private final AiAssistantProperties properties;
    private Assistant assistant;

    public AiAssistantService(ChatModel chatModel,
                              SystemDataTools systemDataTools,
                              AiAssistantProperties properties) {
        this.chatModel = chatModel;
        this.systemDataTools = systemDataTools;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (!properties.isEnabled() || chatModel == null) {
            log.warn("AI 助手未启用或模型未初始化，将使用模拟模式");
            return;
        }
        this.assistant = AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .tools(systemDataTools)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(30))
                .build();
        log.info("AI 助手初始化完成");
    }

    public String chat(String message) {
        if (assistant == null) {
            return fallbackChat(message);
        }
        try {
            return assistant.chat(message);
        } catch (Exception e) {
            log.error("AI 响应失败，切换到模拟模式: {}", e.getMessage());
            return fallbackChat(message);
        }
    }

    /**
     * 模拟模式：当 AI 模型不可用时提供基本响应
     */
    private String fallbackChat(String message) {
        String msg = message.toLowerCase();
        if (msg.contains("统计") || msg.contains("概览") || msg.contains("总览")) {
            return getSystemOverview();
        }
        if (msg.contains("学生") && (msg.contains("多少") || msg.contains("几个") || msg.contains("数量"))) {
            return "当前系统中暂无学生数据。请确保数据库已正确初始化，并在「基础数据」模块中录入学生信息。";
        }
        if (msg.contains("你好") || msg.contains("hello") || msg.contains("hi")) {
            return "你好！我是高校实训管理平台的 AI 助手。我可以帮你查询系统数据概览、统计信息等。请问有什么可以帮助你的？\n\n" +
                   "💡 提示：如需使用完整 AI 功能，请配置并启动 Ollama 或 OpenAI 兼容 API。\n" +
                   "当前处于模拟模式，部分查询功能受限。";
        }
        if (msg.contains("帮助") || msg.contains("功能") || msg.contains("能做什么")) {
            return "我可以帮你完成以下操作：\n\n" +
                   "1. 数据查询 - 查询学生、课程、成绩等数据\n" +
                   "2. 统计分析 - 提供系统数据概览和成绩统计\n" +
                   "3. 系统管理 - 辅助管理系统用户和权限\n\n" +
                   "💡 当前处于模拟模式。连接 AI 模型后可获得更智能的交互体验。";
        }
        return "你好！我是系统 AI 助手。当前处于模拟模式，如需完整功能请配置 AI 模型服务。\n\n" +
               "你可以尝试问：\n" +
               "- 「系统数据概览」\n" +
               "- 「查询所有学院」\n" +
               "- 「成绩统计信息」\n" +
               "- 「你能做什么」";
    }

    private String getSystemOverview() {
        try {
            return systemDataTools.getSystemOverview();
        } catch (Exception e) {
            return "系统数据概览功能暂不可用，请先确保数据库连接正常。";
        }
    }

    public interface Assistant {
        @SystemMessage("""
            你是「高校实训管理平台」的智能数据助手，名叫"小训"。

            你的职责是帮助管理员查询和管理系统中的数据。

            可查询的数据包括：
            - 学院、专业、班级信息
            - 学生、课程、成绩信息
            - 系统用户信息
            - 统计数据和分析

            规则：
            1. 回答要简洁、准确，使用中文
            2. 当用户询问数据时，优先使用提供的工具查询实时数据
            3. 查询到数据后，用友好的方式呈现给用户
            4. 如果工具查询结果为空，友好地告知用户暂无数据
            5. 不要编造数据，只使用工具返回的真实数据
            6. 回答时可以适当使用emoji让对话更友好
            """)
        String chat(String userMessage);
    }
}
