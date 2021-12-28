package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
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
     * @param sysDictListReq 请求字典列表参数
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
     * @param id 字典id
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysDictTypeResp detail(Long id) {
        SysDictType sysDict = sysDictTypeMapper.selectById(id);
        SysDictTypeResp sysDictResp = new SysDictTypeResp();
        BeanUtils.copyProperties(sysDict,sysDictResp);
        return sysDictResp;
    }

    /**
     * 添加请求
     * @param sysDictAddReq 添加字典参数
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int add(SysDictTypeAddReq sysDictAddReq) {
        SysDictType sysDictType = sysDictTypeMapper.findDictByKey(sysDictAddReq.getDictKey());
        Assert.isNull(sysDictType, ResultStatus.DICT_EXIST_ERROR.message);
        SysDictType sysDict = new SysDictType();
        BeanUtils.copyProperties(sysDictAddReq,sysDict);
        return sysDictTypeMapper.insert(sysDict);
    }

    /**
     * 编辑请求
     * @param sysDictEditReq 编辑字典参数
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public int edit(SysDictTypeEditReq sysDictEditReq) {
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(sysDictEditReq,sysDictType);
        return sysDictTypeMapper.updateById(sysDictType);
    }

    /**
     * 删除请求
     * @param id 字典id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        sysDictTypeMapper.deleteById(id);
        sysDictValueMapper.delete(new LambdaQueryWrapper<SysDictValue>()
                .eq(SysDictValue::getDictTypeId,id));
    }

    /**
     * 修改字典状态
     * @param sysDictTypeEnabledReq 修改字典参数
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
