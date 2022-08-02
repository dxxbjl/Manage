package io.github.yangyouwang.crud.system.mapper;

import io.github.yangyouwang.common.base.CrudBaseMapper;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 查询所有字典
     * @return 数据字典列表
     */
    List<SysDictType> selectDictList();
    /**
     * 自定义sql分页
     * @param dictKey 类型key
     * @param dictName 类型名称
     * @return 数据字典列表
     */
    List<SysDictType> selectDictPage(@Param("dictKey") String dictKey,@Param("dictName") String dictName);
}
