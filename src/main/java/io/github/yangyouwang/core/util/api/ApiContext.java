package io.github.yangyouwang.core.util.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取userId上下文环境
 * @author yangyouwang
 */
public class ApiContext {

    private static final String API_CONTEXT_KEY = "userId";

    private static final Map<String, Object> CONTEXT = new ConcurrentHashMap<>(16);

    public static void setUserId(Long userId) {
        CONTEXT.put(API_CONTEXT_KEY, userId);
    }

    public static Long getUserId() {
        if (CONTEXT.containsKey(API_CONTEXT_KEY)) {
            return Long.parseLong(CONTEXT.get(API_CONTEXT_KEY).toString());
        }
        return null;
    }
}