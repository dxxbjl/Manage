package io.github.yangyouwang.crud.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.crud.system.mapper.SysDictTypeMapper;
import io.github.yangyouwang.crud.system.mapper.SysDictValueMapper;
import io.github.yangyouwang.crud.system.entity.SysDictType;
import io.github.yangyouwang.crud.system.entity.SysDictValue;
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
     * @param sysDictType 请求字典列表参数
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public List<SysDictType> list(SysDictType sysDictType) {
         return sysDictTypeMapper.selectDictPage(sysDictType.getDictKey(),sysDictType.getDictName());
    }

    /**
     * 添加请求
     * @param sysDictType 添加字典参数
     * @return 添加状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean add(SysDictType sysDictType) {
        SysDictType old = sysDictTypeMapper.findDictByKey(sysDictType.getDictKey());
        Assert.isNull(old, ResultStatus.DICT_EXIST_ERROR.message);
        return this.save(sysDictType);
    }

    /**
     * 删除请求
     * @param id 字典id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        boolean flag = this.removeById(id);
        if (flag) {
            sysDictValueMapper.delete(new LambdaQueryWrapper<SysDictValue>()
                    .eq(SysDictValue::getDictTypeId,id));
        }
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
