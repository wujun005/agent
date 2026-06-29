package com.example.demo.openai;

import com.example.demo.openai.api.GenerateResponseRequest;
import com.example.demo.openai.api.GenerateResponseResult;
import com.example.demo.sensitive.SensitiveWordGuard;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文本生成接口的 HTTP 入口。
 */
@RestController
@RequestMapping("/api/ai")
@ConditionalOnProperty(prefix = "openai", name = "enabled", havingValue = "true")
public class OpenAiResponseController {

    private final OpenAiResponsesService responsesService;
    private final SensitiveWordGuard sensitiveWordGuard;

    public OpenAiResponseController(
            OpenAiResponsesService responsesService,
            SensitiveWordGuard sensitiveWordGuard
    ) {
        this.responsesService = responsesService;
        this.sensitiveWordGuard = sensitiveWordGuard;
    }

    /**
     * 校验请求内容后，再转发给模型服务处理。
     */
    @PostMapping("/responses")
    public GenerateResponseResult createResponse(@Valid @RequestBody GenerateResponseRequest request) {
        sensitiveWordGuard.validate(request.input(), request.instructions());
        return responsesService.createResponse(request);
    }
}
