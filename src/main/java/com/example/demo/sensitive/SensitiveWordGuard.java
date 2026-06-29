package com.example.demo.sensitive;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 按照当前启用的敏感词列表检查请求文本。
 */
@Service
public class SensitiveWordGuard {

    private final SensitiveWordRepository sensitiveWordRepository;

    public SensitiveWordGuard(SensitiveWordRepository sensitiveWordRepository) {
        this.sensitiveWordRepository = sensitiveWordRepository;
    }

    /**
     * 在调用模型前，同时校验用户输入和附加指令。
     */
    public void validate(String input, String instructions) {
        List<SensitiveWordRule> rules = sensitiveWordRepository.findActiveWords();
        Set<String> hits = new LinkedHashSet<>();

        collectHits(input, rules, hits);
        collectHits(instructions, rules, hits);

        if (!hits.isEmpty()) {
            throw new SensitiveWordBlockedException(List.copyOf(hits));
        }
    }

    /**
     * 收集所有命中的词，并保留命中顺序。
     */
    private void collectHits(String text, List<SensitiveWordRule> rules, Set<String> hits) {
        if (!StringUtils.hasText(text)) {
            return;
        }

        // 统一转小写，便于处理英文大小写；中文词条不受影响。
        String normalizedText = text.toLowerCase(Locale.ROOT);
        for (SensitiveWordRule rule : rules) {
            if (!StringUtils.hasText(rule.word())) {
                continue;
            }

            if (normalizedText.contains(rule.word().toLowerCase(Locale.ROOT))) {
                hits.add(rule.word());
            }
        }
    }
}
