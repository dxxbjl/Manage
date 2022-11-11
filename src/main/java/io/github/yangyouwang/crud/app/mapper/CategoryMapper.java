package io.github.yangyouwang.crud.app.mapper;

import io.github.yangyouwang.crud.app.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author yangyouwang
 * @since 2022-10-30
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 根据id获取分类详情
     * @param id 分类id
     * @return 分类详情
     */
    Category info(Long id);
}
