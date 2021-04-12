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
@Table(name="sys_dictionary_type")
public class SysDictionaryType extends BaseEntity {

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
    @Column(name="dictionary_key")
    private String dictionaryKey;
    /**
     * 类型名称
     */
    @Column(name="dictionary_name")
    private String dictionaryName;
}
