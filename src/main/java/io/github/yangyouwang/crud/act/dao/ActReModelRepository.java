package io.github.yangyouwang.crud.act.dao;

import io.github.yangyouwang.crud.act.model.ActReModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yangyouwang
 * @title: ActReModelRepository
 * @projectName crud
 * @description: 流程模型
 * @date 2021/4/10下午2:01
 */
public interface ActReModelRepository extends JpaRepository<ActReModel,String>, JpaSpecificationExecutor {
}
