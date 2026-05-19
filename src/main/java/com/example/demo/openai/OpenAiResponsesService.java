package com.example.demo.openai;

import com.example.demo.openai.api.GenerateResponseRequest;
import com.example.demo.openai.api.GenerateResponseResult;
import com.openai.client.OpenAIClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseOutputText;
import com.openai.models.responses.ResponseUsage;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OpenAiResponsesService {

    private final OpenAIClient openAIClient;
    private final OpenAiProperties properties;

    public OpenAiResponsesService(OpenAIClient openAIClient, OpenAiProperties properties) {
        this.openAIClient = openAIClient;
        this.properties = properties;
    }

    public GenerateResponseResult createResponse(GenerateResponseRequest request) {
        String model = StringUtils.hasText(request.model()) ? request.model() : properties.getModel();

        ResponseCreateParams.Builder builder = ResponseCreateParams.builder()
                .model(model)
                .input(request.input());

        if (StringUtils.hasText(request.instructions())) {
            builder.instructions(request.instructions());
        }

        if (StringUtils.hasText(request.previousResponseId())) {
            builder.previousResponseId(request.previousResponseId());
        }

        if (request.maxOutputTokens() != null) {
            builder.maxOutputTokens(request.maxOutputTokens().longValue());
        }

        if (request.temperature() != null) {
            builder.temperature(request.temperature());
        }

        Response response = openAIClient.responses().create(builder.build());
        ResponseUsage usage = response.usage().orElse(null);

        return new GenerateResponseResult(
                response.id(),
                model,
                extractOutputText(response),
                usage == null ? null : usage.inputTokens(),
                usage == null ? null : usage.outputTokens(),
                usage == null ? null : usage.totalTokens()
        );
    }

    private String extractOutputText(Response response) {
        return response.output().stream()
                .filter(ResponseOutputItem::isMessage)
                .map(ResponseOutputItem::asMessage)
                .flatMap(message -> message.content().stream())
                .filter(content -> content.outputText().isPresent())
                .map(content -> content.outputText().orElseThrow())
                .map(ResponseOutputText::text)
                .collect(Collectors.joining(System.lineSeparator()))
                .trim();
    }
}
