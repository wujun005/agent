package com.example.demo.sensitive;

import java.util.List;

/**
 * 获取启用中敏感词规则的抽象接口。
 */
public interface SensitiveWordRepository {

    /**
     * 返回所有需要参与请求校验的启用词条。
     */
    List<SensitiveWordRule> findActiveWords();
}
