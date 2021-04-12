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
@Table(name="sys_dictionary_value")
public class SysDictionaryValue extends BaseEntity {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    /**
     * 种类值key
     */
    @Column(name="dictionary_value_key")
    private String dictionaryValueKey;

    /**
     * 种类值名称
     */
    @Column(name="dictionary_value_name")
    private String dictionaryValueName;

    /**
     * 种类ID
     */
    @Column(name="type_id")
    private String typeId;
}
