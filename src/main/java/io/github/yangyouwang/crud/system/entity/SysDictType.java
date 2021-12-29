package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 数据字典类型
 * @date 2021/4/12 11:27
 */
@Data
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 616049205960190106L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 启用
     */
    private String enabled;

    /**
     * 字典类型与字典项 一对多
     */
    @TableField(exist = false)
    private List<SysDictValue> dictValues;
}
