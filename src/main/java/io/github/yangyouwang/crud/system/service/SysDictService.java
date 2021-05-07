package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.core.exception.CrudException;
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
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.*;

/**
 * @author yangyouwang
 * @title: SysDictService
 * @projectName crud
 * @description: 字典业务层
 * @date 2021/4/13下午1:11
 */
@Service
public class SysDictService extends ServiceImpl<SysDictTypeMapper, SysDictType> {

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
        return sysDictTypeMapper.selectPage(new Page<>(sysDictListReq.getPageNum(), sysDictListReq.getPageSize()),
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
        SysDictType sysDict = sysDictTypeMapper.findDictById(id);
        SysDictResp sysDictResp = new SysDictResp();
        BeanUtils.copyProperties(sysDict,sysDictResp);
        ofNullable(sysDict.getDictValues()).ifPresent(result -> {
            List<SysDictValueDto> sysDictValueDtos = result.stream().filter(dictValue ->
                    StringUtils.isNotBlank(dictValue.getDictValueKey())).map(sysDictValue -> {
                SysDictValueDto sysDictValueDto = new SysDictValueDto();
                BeanUtils.copyProperties(sysDictValue, sysDictValueDto);
                return sysDictValueDto;
            }).collect(Collectors.toList());
            sysDictResp.setSysDictValues(sysDictValueDtos);
        });
        return sysDictResp;
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int add(SysDictAddReq sysDictAddReq) {
        SysDictType sysDictType = sysDictTypeMapper.findDictByKey(sysDictAddReq.getDictKey());
        Assert.isNull(sysDictType, "字典已存在");
        SysDictType sysDict = new SysDictType();
        // vo -> po
        BeanUtils.copyProperties(sysDictAddReq,sysDict);
        int flag = sysDictTypeMapper.insert(sysDict);
        if (flag > 0) {
            ofNullable(sysDictAddReq.getSysDictValues()).ifPresent(sysDictValueDtos -> {
                insertDictValueBatch(sysDictValueDtos, sysDict.getId());
            });
            return flag;
        }
        throw new CrudException("添加字典出错");
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysDictEditReq sysDictEditReq) {
        SysDictType sysDictType = new SysDictType();
        // vo -> po
        BeanUtils.copyProperties(sysDictEditReq,sysDictType);
        int flag = sysDictTypeMapper.updateById(sysDictType);
        if (flag > 0) {
            ofNullable(sysDictEditReq.getSysDictValues()).ifPresent(sysDictValueDtos -> {
                insertDictValueBatch(sysDictValueDtos, sysDictType.getId());
            });
            return flag;
        }
        throw new CrudException("编辑字典出错");
    }

    /**
     * 批量新增或者修改字典项
     * @param sysDictValueDtos 字典列表
     * @param id 字典类型id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void insertDictValueBatch(List<SysDictValueDto> sysDictValueDtos, Long id) {
        // 字典项
        List<SysDictValue> sysDictValues = sysDictValueDtos.stream().filter(dictValue ->
                StringUtils.isNotBlank(dictValue.getDictValueKey())).map(dictValue -> {
            SysDictValue sysDictValue = new SysDictValue();
            BeanUtils.copyProperties(dictValue, sysDictValue);
            sysDictValue.setDictTypeId(id);
            return sysDictValue;
        }).collect(Collectors.toList());
        if(sysDictValues.size() == 0) {
            throw new CrudException("字典项不允许为空");
        }
        int flag = sysDictValueMapper.insertBatch(sysDictValues);
        if (flag == 0) {
            throw new CrudException("批量新增或者修改字典项失败");
        }
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int delKey(Long id) {
        int flag = sysDictTypeMapper.deleteById(id);
        if (flag > 0) {
            sysDictValueMapper.delete(new LambdaQueryWrapper<SysDictValue>()
                    .eq(SysDictValue::getDictTypeId,id));
            return flag;
        }
        throw new CrudException("删除字典失败");
    }

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
