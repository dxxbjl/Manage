package io.github.yangyouwang.crud.system.dao;

import io.github.yangyouwang.crud.system.model.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yangyouwang
 * @title: SysLogRepository
 * @projectName crud
 * @description: 系统日志Dao
 * @date 2021/4/19:59 AM
 */
public interface SysLogRepository extends JpaRepository<SysLog,Long>,JpaSpecificationExecutor {
}