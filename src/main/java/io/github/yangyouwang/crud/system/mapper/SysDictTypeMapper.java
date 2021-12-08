package io.github.yangyouwang.crud.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.github.yangyouwang.common.base.CrudBaseMapper;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import org.apache.ibatis.annotations.Param;

/**
 * @author yangyouwang
 * @title: SysDictTypeMapper
 * @projectName crud
 * @description: 数据字典类型Mapper
 * @date 2021/4/12下午8:07
 */
public interface SysDictTypeMapper extends CrudBaseMapper<SysDictType> {
    /**
     * 根据字典别名获取字典
     * @param dictKey 字典key
     * @return 字典
     */
    SysDictType findDictByKey(String dictKey);
    /**
     * 自定义sql分页
     * @param page page
     * @param queryWrapper wrapper查询条件
     * @return 数据字典列表
     */
    IPage selectDictPage(IPage<SysDictType> page, @Param(Constants.WRAPPER) Wrapper<SysDictType> queryWrapper);
}
