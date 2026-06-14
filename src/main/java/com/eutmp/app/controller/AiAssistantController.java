package com.eutmp.app.controller;

import com.eutmp.app.service.AiAssistantService;
import com.eutmp.app.utils.Result;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    @PostMapping("/chat")
    public Result<ChatResponse> chat(@RequestBody ChatRequest request) {
        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            return Result.error("消息不能为空");
        }
        String reply = aiAssistantService.chat(request.getMessage().trim());
        return Result.success(new ChatResponse(reply));
    }

    @GetMapping("/status")
    public Result<StatusResponse> status() {
        return Result.success(new StatusResponse(true, "AI 助手服务运行中"));
    }

    @Data
    public static class ChatRequest {
        private String message;
    }

    @Data
    public static class ChatResponse {
        private final String reply;
    }

    @Data
    public static class StatusResponse {
        private final boolean online;
        private final String message;
    }
}
