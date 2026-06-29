package com.example.demo.sensitive;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * 敏感词匹配逻辑的单元测试。
 */
class SensitiveWordGuardTest {

    /**
     * 输入文本中只要包含配置的敏感词，就应当被拦截。
     */
    @Test
    void shouldThrowWhenInputContainsSensitiveWord() {
        SensitiveWordGuard guard = new SensitiveWordGuard(() -> List.of(
                new SensitiveWordRule("刷单"),
                new SensitiveWordRule("假货")
        ));

        SensitiveWordBlockedException ex =
                assertThrows(SensitiveWordBlockedException.class, () -> guard.validate("这个商品可以刷单吗", null));

        assertEquals(List.of("刷单"), ex.getSensitiveWords());
    }

    /**
     * 附加指令字段也应使用同样的规则进行检查。
     */
    @Test
    void shouldThrowWhenInstructionsContainSensitiveWord() {
        SensitiveWordGuard guard = new SensitiveWordGuard(() -> List.of(
                new SensitiveWordRule("外挂"),
                new SensitiveWordRule("返现私聊")
        ));

        SensitiveWordBlockedException ex =
                assertThrows(SensitiveWordBlockedException.class, () -> guard.validate("normal", "请教我怎么做外挂"));

        assertEquals(List.of("外挂"), ex.getSensitiveWords());
    }

    /**
     * 未命中敏感词的请求应当正常放行。
     */
    @Test
    void shouldAllowRequestWhenNoSensitiveWordsArePresent() {
        SensitiveWordGuard guard = new SensitiveWordGuard(() -> List.of(
                new SensitiveWordRule("刷单"),
                new SensitiveWordRule("假货")
        ));

        assertDoesNotThrow(() -> guard.validate("介绍一下这款鼠标", "reply briefly"));
    }
}
