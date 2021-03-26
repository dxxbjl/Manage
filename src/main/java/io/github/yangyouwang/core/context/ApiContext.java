package io.github.yangyouwang.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取userId上下文环境
 * @author yangyouwang
 */
public class ApiContext {

    private static final Map<String, Object> CONTEXT = new ConcurrentHashMap<>(16);

    public static void setUserId(String userId) {
        CONTEXT.put("userId", userId);
    }

    public static String getUserId() {
        if (CONTEXT.containsKey("userId")) {
            return CONTEXT.get("userId").toString();
        }
        return null;
    }
}