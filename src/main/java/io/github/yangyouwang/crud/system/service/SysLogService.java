package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.crud.system.mapper.SysLogMapper;
import io.github.yangyouwang.crud.system.entity.SysLog;
import io.github.yangyouwang.crud.system.model.params.SysLogListDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author yangyouwang
 * @title: SysLogService
 * @projectName crud
 * @description: 系统日志业务层
 * @date 2021/4/111:08 AM
 */
@Service
public class SysLogService extends ServiceImpl<SysLogMapper,SysLog> {

    /**
     * 列表请求
     * @param sysLogListDTO 日志列表对象
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public IPage list(SysLogListDTO sysLogListDTO) {
        return this.page(new Page<>(sysLogListDTO.getPageNum(), sysLogListDTO.getPageSize()),
                new LambdaQueryWrapper<SysLog>()
                        .like(StringUtils.isNotBlank(sysLogListDTO.getClassName()), SysLog::getClassName , sysLogListDTO.getClassName())
                        .like(StringUtils.isNotBlank(sysLogListDTO.getMethodName()), SysLog::getMethodName , sysLogListDTO.getMethodName()));
    }
}