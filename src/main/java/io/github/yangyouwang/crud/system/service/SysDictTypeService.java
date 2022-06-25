package io.github.yangyouwang.crud.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
import io.github.yangyouwang.crud.system.model.params.SysDictTypeAddDTO;
import io.github.yangyouwang.crud.system.model.params.SysDictTypeEditDTO;
import io.github.yangyouwang.common.domain.EnabledDTO;
import io.github.yangyouwang.crud.system.model.params.SysDictTypeListDTO;
import io.github.yangyouwang.crud.system.model.result.SysDictTypeDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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

    @Resource
    private HttpServletResponse response;

    /**
     * 列表请求
     * @param sysDictListDTO 请求字典列表参数
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public IPage list(SysDictTypeListDTO sysDictListDTO) {
         return sysDictTypeMapper.selectDictPage(new Page<>(sysDictListDTO.getPageNum(), sysDictListDTO.getPageSize()),
                new LambdaQueryWrapper<SysDictType>()
                        .like(StringUtils.isNotBlank(sysDictListDTO.getDictName()), SysDictType::getDictName, sysDictListDTO.getDictName())
                        .like(StringUtils.isNotBlank(sysDictListDTO.getDictKey()), SysDictType::getDictKey, sysDictListDTO.getDictKey()));
    }

    /**
     * 跳转编辑
     * @param id 字典id
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysDictTypeDTO detail(Long id) {
        SysDictType sysDict = this.getById(id);
        SysDictTypeDTO sysDictTypeDTO = new SysDictTypeDTO();
        BeanUtils.copyProperties(sysDict,sysDictTypeDTO);
        return sysDictTypeDTO;
    }

    /**
     * 添加请求
     * @param sysDictTypeAddDTO 添加字典参数
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean add(SysDictTypeAddDTO sysDictTypeAddDTO) {
        SysDictType sysDictType = sysDictTypeMapper.findDictByKey(sysDictTypeAddDTO.getDictKey());
        Assert.isNull(sysDictType, ResultStatus.DICT_EXIST_ERROR.message);
        SysDictType sysDict = new SysDictType();
        BeanUtils.copyProperties(sysDictTypeAddDTO,sysDict);
        return this.save(sysDict);
    }

    /**
     * 编辑请求
     * @param sysDictTypeEditDTO 编辑字典参数
     * @return 编辑状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean edit(SysDictTypeEditDTO sysDictTypeEditDTO) {
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(sysDictTypeEditDTO,sysDictType);
        return this.updateById(sysDictType);
    }

    /**
     * 删除请求
     * @param id 字典id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        this.removeById(id);
        sysDictValueMapper.delete(new LambdaQueryWrapper<SysDictValue>()
                .eq(SysDictValue::getDictTypeId,id));
    }

    /**
     * 修改字典状态
     * @param enabledDTO 修改字典参数
     * @return 修改状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean changeDictType(EnabledDTO enabledDTO) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setId(enabledDTO.getId());
        sysDictType.setEnabled(enabledDTO.getEnabled());
        return this.updateById(sysDictType);
    }

    /**
     * 缓存字典
     */
    public void cacheDict() {
        List<SysDictType> sysDictTypes = sysDictTypeMapper.selectDictList();
        sysDictTypes.forEach(sysDictType -> {
            List<SysDictValue> dictValues = sysDictType.getDictValues();
            String dictValue = JSONArray.parseArray(JSON.toJSONString(dictValues)).toJSONString();
            try {
                Cookie cookie = new Cookie(sysDictType.getDictKey(), URLEncoder.encode(dictValue, "utf-8"));
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setPath("/");
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }
}
