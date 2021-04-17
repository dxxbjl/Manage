package io.github.yangyouwang.crud.system.dao;

import io.github.yangyouwang.crud.system.model.SysTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * task任务dao接口
 * @author yangyouwang
 */
public interface SysTaskRepository extends JpaRepository<SysTask,Long>, JpaSpecificationExecutor {
}
