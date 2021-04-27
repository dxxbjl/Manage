package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.model.SysDictType;
import io.github.yangyouwang.crud.system.model.SysDictValue;
import io.github.yangyouwang.crud.system.model.dao.SysDictValueDto;
import io.github.yangyouwang.crud.system.model.req.SysDictAddReq;
import io.github.yangyouwang.crud.system.model.req.SysDictEditReq;
import io.github.yangyouwang.crud.system.model.req.SysDictEnabledReq;
import io.github.yangyouwang.crud.system.model.req.SysDictListReq;
import io.github.yangyouwang.crud.system.model.resp.SysDictResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangyouwang
 * @title: SysDictService
 * @projectName crud
 * @description: 字典业务层
 * @date 2021/4/13下午1:11
 */
@Service
public class SysDictService {

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    @Resource
    private SysDictValueMapper sysDictValueMapper;

    /**
     * 列表请求
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public IPage<SysDictType> list(SysDictListReq sysDictListReq) {
        return sysDictTypeMapper.selectPage(new Page<>(sysDictListReq.getPageNum() - 1, sysDictListReq.getPageSize()),
                new LambdaQueryWrapper<SysDictType>()
                        .like(StringUtils.isNotBlank(sysDictListReq.getDictName()), SysDictType::getDictName , sysDictListReq.getDictName())
                        .like(StringUtils.isNotBlank(sysDictListReq.getDictKey()), SysDictType::getDictKey , sysDictListReq.getDictKey()));
    }

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysDictResp detail(Long id) {
        SysDictType sysDict = sysDictTypeMapper.selectById(id);
        SysDictResp sysDictResp = new SysDictResp();
        BeanUtils.copyProperties(sysDict,sysDictResp);
        List<SysDictValueDto> sysDictValues = sysDict.getDictValues().stream().map(sysDictValue -> {
            SysDictValueDto sysDictValueDto = new SysDictValueDto();
            BeanUtils.copyProperties(sysDictValue, sysDictValueDto);
            return sysDictValueDto;
        }).collect(Collectors.toList());
        sysDictResp.setSysDictValues(sysDictValues);
        return sysDictResp;
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int add(SysDictAddReq sysDictAddReq) {
        SysDictType sysDictType = sysDictTypeMapper.findByDictKey(sysDictAddReq.getDictKey());
        Assert.isNull(sysDictType, "字典已存在");
        SysDictType sysDict = new SysDictType();
        BeanUtils.copyProperties(sysDictAddReq,sysDict);
        List<SysDictValue> sysDictValues =  getSysDictValues(sysDictAddReq.getSysDictValues());
        sysDict.setDictValues(sysDictValues);
        return sysDictTypeMapper.insert(sysDict);
    }

    /**
     * 字典dto转bean
     * @param sysDictValueDto 字典dto
     * @return bean
     */
    private List<SysDictValue> getSysDictValues(List<SysDictValueDto> sysDictValueDto) {
        return sysDictValueDto.stream().filter(dictValue -> StringUtils.isNotBlank(dictValue.getDictValueKey())).map(dictValue -> {
                SysDictValue sysDictValue = new SysDictValue();
                BeanUtils.copyProperties(dictValue, sysDictValue);
                return sysDictValue;
            }).collect(Collectors.toList());
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysDictEditReq sysDictEditReq) {
        SysDictType sysDictType = new SysDictType();
        List<SysDictValue> sysDictValues = getSysDictValues(sysDictEditReq.getSysDictValues());
        sysDictType.setDictValues(sysDictValues);
        BeanUtils.copyProperties(sysDictEditReq,sysDictType);
        return sysDictTypeMapper.updateById(sysDictType);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int delKey(Long id) {
       return sysDictTypeMapper.deleteById(id);
    }

    /**
     * 根据字典类型获取字典列表
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public List<SysDictValueDto> getDictValues(String dictKey) {
        SysDictType sysDictType = sysDictTypeMapper.findByDictKey(dictKey);
        if (Constants.ENABLED_YES.equals(sysDictType.getEnabled())) {
            return sysDictType.getDictValues().stream().map(sysDictValue -> {
                SysDictValueDto sysDictValueDto = new SysDictValueDto();
                BeanUtils.copyProperties(sysDictValue,sysDictValueDto);
                return sysDictValueDto;
            }).collect(Collectors.toList());
        }
       return Collections.emptyList();
    }

    /**
     * 修改字典状态
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int changeDict(SysDictEnabledReq sysDictEnabledReq) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setId(sysDictEnabledReq.getId());
        sysDictType.setEnabled(sysDictEnabledReq.getEnabled());
        return sysDictTypeMapper.updateById(sysDictType);
    }

    /**
     * 删除字典值请求
     * @return 删除状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int delValue(Long id) {
        return sysDictValueMapper.deleteById(id);
    }
}
