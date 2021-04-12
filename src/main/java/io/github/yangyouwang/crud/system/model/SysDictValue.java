package io.github.yangyouwang.crud.system.model;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 数据字典值
 * @date 2021/4/12 11:27
 */
@Data
@Entity
@Table(name="sys_dict_value")
public class SysDictValue extends BaseEntity {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    /**
     * 字典值key
     */
    @Column(name="dict_value_key")
    private String dictValueKey;

    /**
     * 字典值名称
     */
    @Column(name="dict_value_name")
    private String dictValueName;

    /**
     * 启用
     */
    @Column(name="enabled")
    private String enabled;
    /**
     * 显示顺序
     */
    @Column(name="order_num")
    private Integer orderNum;
    /**
     * 字典类型ID
     */
    @Column(name="dict_type_id")
    private String dictTypeId;
}
