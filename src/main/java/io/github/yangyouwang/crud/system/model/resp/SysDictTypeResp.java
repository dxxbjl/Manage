package io.github.yangyouwang.crud.system.model.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysDictResp
 * @projectName crud
 * @description: 字典响应
 * @date 2021/4/13下午2:21
 */
@Data
public class SysDictTypeResp implements Serializable {

    private static final long serialVersionUID = 5385502603841755082L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 类型key
     */
    private String dictKey;
    /**
     * 类型名称
     */
    private String dictName;
    /**
     * 启用
     */
    private String enabled;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /** 备注 */
    private String remark;
}
