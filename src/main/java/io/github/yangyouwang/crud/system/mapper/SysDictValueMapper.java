package io.github.yangyouwang.crud.system.mapper;

import io.github.yangyouwang.common.base.CrudBaseMapper;
import io.github.yangyouwang.crud.system.model.SysDictValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典值Mapper
 * @author yangyouwang
 */
public interface SysDictValueMapper extends CrudBaseMapper<SysDictValue> {
    /**
     * 批量插入字典项
     * @param sysDictValues 字典项
     * @return 新增或修改状态
     */
    int insertBatch(@Param("list")List<SysDictValue> sysDictValues);
}
