package io.github.yangyouwang.crud.system.mapper;

import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.crud.system.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author yangyouwang
 * @since 2022-09-03
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 获取部门列表
     * @return 部门列表
     */
    List<TreeSelectNode> getDeptTree();
}
