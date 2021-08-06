package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.model.dto.SysDictValueDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author yangyouwang
 * @title: SysDictService
 * @projectName crud
 * @description: 字典业务层
 * @date 2021/4/13下午1:11
 */
@Service
public class SysDictValueService extends ServiceImpl<SysDictValueMapper, SysDictValue> {

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    @Resource
    private SysDictValueMapper sysDictValueMapper;

    /**
     * 根据字典类型获取字典列表
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public List<SysDictValueDto> getDictValues(String dictKey) {
        SysDictType sysDictType = sysDictTypeMapper.findDictByKey(dictKey);
        if (Objects.nonNull(sysDictType)) {
            return sysDictType.getDictValues().stream().map(sysDictValue -> {
                SysDictValueDto sysDictValueDto = new SysDictValueDto();
                BeanUtils.copyProperties(sysDictValue,sysDictValueDto);
                return sysDictValueDto;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    /**
     * 删除字典值请求
     * @return 删除状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int del(Long id) {
        return sysDictValueMapper.deleteById(id);
    }
}
