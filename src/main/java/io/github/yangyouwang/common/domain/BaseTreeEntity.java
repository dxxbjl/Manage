package io.github.yangyouwang.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Tree基类
 * 
 * @author crud
 */
@Data
public abstract class BaseTreeEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    /** 主键id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 上级ID */
    private Long parentId;

    /** 上级名称 */
    @TableField(exist = false)
    private String parentName;

    /** 创建者 */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新者 */
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 逻辑删除 0 否、1 是 */
    @TableLogic
    private Integer deleted;

    /** 备注 */
    private String remark;
}