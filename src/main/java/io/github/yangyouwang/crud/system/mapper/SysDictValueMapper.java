package io.github.yangyouwang.crud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yangyouwang.crud.system.model.SysDictValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典值Mapper
 * @author yangyouwang
 */
public interface SysDictValueMapper extends BaseMapper<SysDictValue> {
    /**
     * 批量插入字典项
     * @param sysDictValues 字典项
     * @return 新增或修改状态
     */
    int insertBatch(@Param("list")List<SysDictValue> sysDictValues);
}
