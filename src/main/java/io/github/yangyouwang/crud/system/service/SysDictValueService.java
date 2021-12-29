package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.model.req.SysDictValueAddReq;
import io.github.yangyouwang.crud.system.model.req.SysDictValueEditReq;
import io.github.yangyouwang.crud.system.model.resp.SysDictValueResp;
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

    @Resource
    private SysDictValueMapper sysDictValueMapper;

    /**
     * 根据字典类型获取字典列表
     * @param dictKey 字典key
     * @return 请求列表
     */
    @Cacheable(cacheNames = {"dictValues"},key = "#dictKey")
    @Transactional(readOnly = true)
    public List<SysDictValueResp> getDictValues(String dictKey) {
        SysDictType sysDictType = sysDictTypeMapper.findDictByKey(dictKey);
        if (Objects.nonNull(sysDictType)) {
            return sysDictType.getDictValues().stream().map(sysDictValue -> {
                SysDictValueResp sysDictValueResp = new SysDictValueResp();
                BeanUtils.copyProperties(sysDictValue,sysDictValueResp);
                return sysDictValueResp;
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
    public SysDictValueResp detail(Long id) {
        SysDictValue sysDictValue = sysDictValueMapper.selectById(id);
        SysDictValueResp sysDictValueResp = new SysDictValueResp();
        BeanUtils.copyProperties(sysDictValue,sysDictValueResp);
        return sysDictValueResp;
    }
    /**
     * 添加请求
     * @param sysDictValueAddReq 添加字典值对象
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int add(SysDictValueAddReq sysDictValueAddReq) {
        SysDictValue sysDictValue = new SysDictValue();
        BeanUtils.copyProperties(sysDictValueAddReq,sysDictValue);
        return sysDictValueMapper.insert(sysDictValue);
    }
    /**
     * 编辑请求
     * @param sysDictValueEditReq 编辑字典值对象
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysDictValueEditReq sysDictValueEditReq) {
        SysDictValue sysDictValue = new SysDictValue();
        BeanUtils.copyProperties(sysDictValueEditReq,sysDictValue);
        return sysDictValueMapper.updateById(sysDictValue);
    }
}
