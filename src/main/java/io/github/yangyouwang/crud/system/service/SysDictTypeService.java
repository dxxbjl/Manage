package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.exception.CrudException;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
import io.github.yangyouwang.crud.system.model.dto.SysDictValueDto;
import io.github.yangyouwang.crud.system.model.req.SysDictTypeAddReq;
import io.github.yangyouwang.crud.system.model.req.SysDictTypeEditReq;
import io.github.yangyouwang.crud.system.model.req.SysDictTypeEnabledReq;
import io.github.yangyouwang.crud.system.model.req.SysDictTypeListReq;
import io.github.yangyouwang.crud.system.model.resp.SysDictTypeResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.util.List;
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
public class SysDictTypeService extends ServiceImpl<SysDictTypeMapper, SysDictType> {

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    @Resource
    private SysDictValueMapper sysDictValueMapper;

    /**
     * 列表请求
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public IPage list(SysDictTypeListReq sysDictListReq) {
         return sysDictTypeMapper.selectDictPage(new Page<>(sysDictListReq.getPageNum(), sysDictListReq.getPageSize()),
                new LambdaQueryWrapper<SysDictType>()
                        .like(StringUtils.isNotBlank(sysDictListReq.getDictName()), SysDictType::getDictName, sysDictListReq.getDictName())
                        .like(StringUtils.isNotBlank(sysDictListReq.getDictKey()), SysDictType::getDictKey, sysDictListReq.getDictKey()));
    }

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysDictTypeResp detail(Long id) {
        SysDictType sysDict = sysDictTypeMapper.findDictById(id);
        SysDictTypeResp sysDictResp = new SysDictTypeResp();
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
    public int add(SysDictTypeAddReq sysDictAddReq) {
        SysDictType sysDictType = sysDictTypeMapper.findDictByKey(sysDictAddReq.getDictKey());
        Assert.isNull(sysDictType, ResultStatus.DICT_EXIST_ERROR.message);
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
        throw new CrudException(ResultStatus.SAVE_DATA_ERROR);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysDictTypeEditReq sysDictEditReq) {
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
        throw new CrudException(ResultStatus.UPDATE_DATA_ERROR);
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
            throw new CrudException(ResultStatus.DICT_ITEM_NULL_ERROR);
        }
        int flag = sysDictValueMapper.insertBatch(sysDictValues);
        if (flag == 0) {
            throw new CrudException(ResultStatus.BATCH_INSTALL_DICT_ERROR);
        }
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int del(Long id) {
        int flag = sysDictTypeMapper.deleteById(id);
        if (flag > 0) {
            sysDictValueMapper.delete(new LambdaQueryWrapper<SysDictValue>()
                    .eq(SysDictValue::getDictTypeId,id));
            return flag;
        }
        throw new CrudException(ResultStatus.DELETE_DATA_ERROR);
    }

    /**
     * 修改字典状态
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int changeDictType(SysDictTypeEnabledReq sysDictTypeEnabledReq) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setId(sysDictTypeEnabledReq.getId());
        sysDictType.setEnabled(sysDictTypeEnabledReq.getEnabled());
        return sysDictTypeMapper.updateById(sysDictType);
    }

}
