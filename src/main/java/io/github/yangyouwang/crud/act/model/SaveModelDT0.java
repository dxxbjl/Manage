package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SaveModelDT0
 * @projectName crud
 * @description: 请求参数
 * @date 2020/7/25下午2:16
 */
@Data
public class SaveModelDT0 implements Serializable {

    private static final long serialVersionUID = 7055017795601515186L;
    private String name;

    private String description;

    private String json_xml;

    private String svg_xml;
}
