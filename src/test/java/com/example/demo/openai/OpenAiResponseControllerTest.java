package com.example.demo.openai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.demo.openai.api.GenerateResponseRequest;
import com.example.demo.openai.api.GenerateResponseResult;
import com.example.demo.sensitive.SensitiveWordBlockedException;
import com.example.demo.sensitive.SensitiveWordGuard;
import org.junit.jupiter.api.Test;

/**
 * 控制器边界上的请求校验单元测试。
 */
class OpenAiResponseControllerTest {

    /**
     * 验证正常请求会先通过校验，再继续调用服务层。
     */
    @Test
    void shouldValidateRequestBeforeCallingService() {
        OpenAiResponsesService responsesService = mock(OpenAiResponsesService.class);
        SensitiveWordGuard sensitiveWordGuard = mock(SensitiveWordGuard.class);
        OpenAiResponseController controller = new OpenAiResponseController(responsesService, sensitiveWordGuard);
        GenerateResponseRequest request = new GenerateResponseRequest(
                "normal request",
                "reply briefly",
                null,
                null,
                null,
                null
        );
        GenerateResponseResult expected = new GenerateResponseResult("resp_123", "qwen3.5-plus", "ok", 1L, 2L, 3L);

        when(responsesService.createResponse(request)).thenReturn(expected);

        GenerateResponseResult actual = controller.createResponse(request);

        verify(sensitiveWordGuard).validate("normal request", "reply briefly");
        verify(responsesService).createResponse(request);
        assertSame(expected, actual);
    }

    /**
     * 验证命中敏感词的请求不会继续进入 OpenAI 服务层。
     */
    @Test
    void shouldStopWhenSensitiveWordDetected() {
        OpenAiResponsesService responsesService = mock(OpenAiResponsesService.class);
        SensitiveWordGuard sensitiveWordGuard = mock(SensitiveWordGuard.class);
        OpenAiResponseController controller = new OpenAiResponseController(responsesService, sensitiveWordGuard);
        GenerateResponseRequest request = new GenerateResponseRequest(
                "我要刷单",
                "reply briefly",
                null,
                null,
                null,
                null
        );

        org.mockito.Mockito.doThrow(new SensitiveWordBlockedException(java.util.List.of("刷单")))
                .when(sensitiveWordGuard)
                .validate("我要刷单", "reply briefly");

        SensitiveWordBlockedException ex =
                assertThrows(SensitiveWordBlockedException.class, () -> controller.createResponse(request));

        verify(sensitiveWordGuard).validate("我要刷单", "reply briefly");
        verifyNoInteractions(responsesService);
        assertEquals(java.util.List.of("刷单"), ex.getSensitiveWords());
    }
}
