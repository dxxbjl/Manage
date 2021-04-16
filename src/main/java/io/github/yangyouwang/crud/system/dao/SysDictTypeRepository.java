package io.github.yangyouwang.crud.system.dao;

import io.github.yangyouwang.crud.system.model.SysDictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yangyouwang
 * @title: SysDictionaryTypeRepository
 * @projectName crud
 * @description: 数据字典类型DAO
 * @date 2021/4/12下午8:07
 */
public interface SysDictTypeRepository extends JpaRepository<SysDictType,Long>, JpaSpecificationExecutor {
    /**
     * 根据字典别名获取字典
     * @return 字典
     */
    SysDictType findByDictKey(String dictKey);
}
