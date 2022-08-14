package io.github.yangyouwang.core.util;

import org.quartz.Trigger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
/**
 * @author yangyouwang
 * @title: StringUtil
 * @projectName crud
 * @description: 字符串工具类
 * @date 2021/3/312:34 PM
 */
public class StringUtil {

    /**
     * 产生4位随机字符串
     */
    public static String getCheckCode() {
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 获取job的调度状态
     * @param triggerState job调度状态key
     * @return job调度状态
     */
    public static String getTriggerStateCN(Trigger.TriggerState triggerState) {
        Map<Trigger.TriggerState, String> map = new LinkedHashMap<>();
        map.put(Trigger.TriggerState.BLOCKED, "阻塞");
        map.put(Trigger.TriggerState.COMPLETE, "完成");
        map.put(Trigger.TriggerState.ERROR, "出错");
        map.put(Trigger.TriggerState.NONE, "不存在");
        map.put(Trigger.TriggerState.NORMAL, "正常");
        map.put(Trigger.TriggerState.PAUSED, "暂停");
        return map.get(triggerState);
    }
}
