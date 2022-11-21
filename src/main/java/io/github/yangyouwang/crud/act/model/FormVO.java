package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 流程表单
 */
@Data
public class FormVO {

    /**
     * 是否外置表单
     */
    private Boolean hasStartFormKey;
    /**
     * 动态表单配置
     */
    private List<FormPropertyVO> formProperties;

    /**
     * 动态表单
     */
    @Data
    public static class FormPropertyVO {
        /**
         * 表单ID
         */
        private String id;
        /**
         * 表单名称
         */
        private String name;
        /**
         * 表单类型
         */
        private String typeName;
        /**
         * 枚举值
         */
        private Object values;
        /**
         * 日期类型
         */
        private Object datePatterns;
        /**
         * 表单值
         */
        private String value;
    }
}
