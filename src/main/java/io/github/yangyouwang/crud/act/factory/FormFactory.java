package io.github.yangyouwang.crud.act.factory;

import io.github.yangyouwang.crud.act.model.FormVO;
import org.activiti.engine.form.FormProperty;

/**
 * 表单静态工厂类
 */
public class FormFactory {

    /**
     * 创建表单配置VO
     * @param formProperty 表单配置Bean
     * @return 表单配置VO
     */
    public static FormVO.FormPropertyVO createFormProperty(FormProperty formProperty) {
        FormVO.FormPropertyVO formPropertyVO = new FormVO.FormPropertyVO();
        formPropertyVO.setId(formProperty.getId());
        formPropertyVO.setName(formProperty.getName());
        formPropertyVO.setValue(formProperty.getValue());
        String type = formProperty.getType().getName();
        formPropertyVO.setTypeName(type);
        if("enum".equals(type)) {
            formPropertyVO.setValues(formProperty.getType().getInformation("values"));
        } else if("date".equals(type)){
            formPropertyVO.setDatePatterns(formProperty.getType().getInformation("datePattern"));
        }
        return formPropertyVO;
    }
}
