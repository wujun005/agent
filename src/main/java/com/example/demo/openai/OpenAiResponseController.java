package com.example.demo.openai;

import com.example.demo.openai.api.GenerateResponseRequest;
import com.example.demo.openai.api.GenerateResponseResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class OpenAiResponseController {

    private final OpenAiResponsesService responsesService;

    public OpenAiResponseController(OpenAiResponsesService responsesService) {
        this.responsesService = responsesService;
    }

    @PostMapping("/responses")
    public GenerateResponseResult createResponse(@Valid @RequestBody GenerateResponseRequest request) {
        return responsesService.createResponse(request);
    }
}
