package io.github.yangyouwang.crud.system.mapper;

import io.github.yangyouwang.crud.system.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
     * 根据id查询部门详情
     * @param id 部门id
     * @return 部门详情
     */
    SysDept info(Long id);
}
