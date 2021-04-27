package io.github.yangyouwang.crud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yangyouwang.crud.system.model.SysDictType;

/**
 * @author yangyouwang
 * @title: SysDictTypeMapper
 * @projectName crud
 * @description: 数据字典类型Mapper
 * @date 2021/4/12下午8:07
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    /**
     * 根据字典别名获取字典
     * @param dictKey 字典key
     * @return 字典
     */
    SysDictType findByDictKey(String dictKey);
}
