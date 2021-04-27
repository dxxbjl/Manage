package io.github.yangyouwang.crud.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 数据字典类型
 * @date 2021/4/12 11:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends BaseEntity {
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
