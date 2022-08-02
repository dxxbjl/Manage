package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.crud.system.mapper.SysLogMapper;
import io.github.yangyouwang.crud.system.entity.SysLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
     * @param sysLog 日志列表对象
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public List<SysLog> list(SysLog sysLog) {
        return this.list(new LambdaQueryWrapper<SysLog>()
                        .like(StringUtils.isNotBlank(sysLog.getClassName()), SysLog::getClassName , sysLog.getClassName())
                        .like(StringUtils.isNotBlank(sysLog.getMethodName()), SysLog::getMethodName , sysLog.getMethodName()));
    }
}