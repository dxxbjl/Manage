package io.github.yangyouwang.crud.system.model;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 数据字典类型
 * @date 2021/4/12 11:27
 */
@Data
@Entity
@Table(name="sys_dict_type")
public class SysDictType extends BaseEntity {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    /**
     * 类型key
     */
    @Column(name="dict_key")
    private String dictKey;
    /**
     * 类型名称
     */
    @Column(name="dict_name")
    private String dictName;
    /**
     * 显示顺序
     */
    @Column(name="order_num")
    private Integer orderNum;
    /**
     * 启用
     */
    @Column(name="enabled")
    private String enabled;
}
