package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 数据字典值
 * @date 2021/4/12 11:27
 */
@Data
@TableName("sys_dict_value")
@ApiModel(value="SysDictValue对象", description="数据字典项表")
public class SysDictValue extends BaseEntity {
    /**
     * 字典类型id
     */
    private Long dictTypeId;
    /**
     * 字典值key
     */
    private String dictValueKey;

    /**
     * 字典值名称
     */
    private String dictValueName;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 启用
     */
    private String enabled;
}
