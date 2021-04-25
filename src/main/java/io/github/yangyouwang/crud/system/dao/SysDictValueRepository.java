package io.github.yangyouwang.crud.system.dao;

import io.github.yangyouwang.crud.system.model.SysDictValue;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 数据字典值Dao
 * @author yangyouwang
 */
public interface SysDictValueRepository extends JpaRepository<SysDictValue,Long> {
}
