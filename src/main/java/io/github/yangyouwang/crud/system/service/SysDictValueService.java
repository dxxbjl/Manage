package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.CacheConsts;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.model.params.SysDictValueAddDTO;
import io.github.yangyouwang.crud.system.model.params.SysDictValueEditDTO;
import io.github.yangyouwang.crud.system.model.result.SysDictValueDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 根据字典类型获取字典列表
     * @param dictKey 字典key
     * @return 请求列表
     */
    @Cacheable(cacheNames = {CacheConsts.DICT_VALUES_KEY},key = "#dictKey")
    @Transactional(readOnly = true)
    public List<SysDictValueDTO> getDictValues(String dictKey) {
        SysDictType sysDictType = sysDictTypeMapper.findDictByKey(dictKey);
        if (Objects.nonNull(sysDictType)) {
            return sysDictType.getDictValues().stream().map(sysDictValue -> {
                SysDictValueDTO sysDictValueDTO = new SysDictValueDTO();
                BeanUtils.copyProperties(sysDictValue,sysDictValueDTO);
                return sysDictValueDTO;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    /**
     * 获取字典项详情
     * @param id 字典项id
     * @return 字典项详情
     */
    @Transactional(readOnly = true)
    public SysDictValueDTO detail(Long id) {
        SysDictValue sysDictValue = this.getById(id);
        SysDictValueDTO sysDictValueDTO = new SysDictValueDTO();
        BeanUtils.copyProperties(sysDictValue,sysDictValueDTO);
        return sysDictValueDTO;
    }
    /**
     * 添加请求
     * @param sysDictValueAddDTO 添加字典值对象
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean add(SysDictValueAddDTO sysDictValueAddDTO) {
        SysDictValue sysDictValue = new SysDictValue();
        BeanUtils.copyProperties(sysDictValueAddDTO,sysDictValue);
        return this.save(sysDictValue);
    }
    /**
     * 编辑请求
     * @param sysDictValueEditDTO 编辑字典值对象
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean edit(SysDictValueEditDTO sysDictValueEditDTO) {
        SysDictValue sysDictValue = new SysDictValue();
        BeanUtils.copyProperties(sysDictValueEditDTO,sysDictValue);
        return this.updateById(sysDictValue);
    }
}
