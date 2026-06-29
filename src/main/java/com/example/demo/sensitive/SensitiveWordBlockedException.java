package com.example.demo.sensitive;

import java.util.List;

/**
 * 当请求中命中一个或多个敏感词时抛出该异常。
 */
public class SensitiveWordBlockedException extends RuntimeException {

    private final List<String> sensitiveWords;

    public SensitiveWordBlockedException(List<String> sensitiveWords) {
        super("Sensitive content detected in request");
        this.sensitiveWords = sensitiveWords;
    }

    public List<String> getSensitiveWords() {
        return sensitiveWords;
    }
}
