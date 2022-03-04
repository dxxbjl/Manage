package io.github.yangyouwang.crud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.config.SchedulingConfig;
import io.github.yangyouwang.crud.system.mapper.SysTaskMapper;
import io.github.yangyouwang.crud.system.entity.SysTask;
import io.github.yangyouwang.crud.system.model.params.SysTaskAddDTO;
import io.github.yangyouwang.crud.system.model.params.SysTaskListDTO;
import io.github.yangyouwang.crud.system.model.result.SysTaskDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yangyouwang
 * @title: SysTaskService
 * @projectName crud
 * @description: 任务业务层
 * @date 2021/4/10上午10:26
 */
@Service
public class SysTaskService extends ServiceImpl<SysTaskMapper, SysTask> {

    @Resource
    private SchedulingConfig schedulingConfig;
    /**
     * 跳转编辑
     * @param id 任务id
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysTaskDTO detail(Long id) {
        SysTask sysTask = this.getById(id);
        SysTaskDTO sysTaskDTO = new SysTaskDTO();
        BeanUtils.copyProperties(sysTask,sysTaskDTO);
        return sysTaskDTO;
    }

    /**
     * 列表请求
     * @param sysTaskListDTO 请求任务列表对象
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public IPage list(SysTaskListDTO sysTaskListDTO) {
        return this.page(new Page<>(sysTaskListDTO.getPageNum(), sysTaskListDTO.getPageSize()),
                new LambdaQueryWrapper<SysTask>()
                        .like(StringUtils.isNotBlank(sysTaskListDTO.getName()), SysTask::getName , sysTaskListDTO.getName())
                        .like(StringUtils.isNotBlank(sysTaskListDTO.getClassName()), SysTask::getClassName , sysTaskListDTO.getClassName()));
    }

    /**
     * 添加请求
     * @param sysTaskAddDTO 添加任务对象
     * @return 添加任务状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean add(SysTaskAddDTO sysTaskAddDTO) {
        SysTask sysTask = new SysTask();
        BeanUtils.copyProperties(sysTaskAddDTO,sysTask);
        if (ConfigConsts.ENABLED_YES.equals(sysTask.getEnabled())) {
            // 添加任务
            schedulingConfig.addTriggerTask(sysTask.getName(),sysTask.getClassName(),sysTask.getMethodName(),sysTask.getCron());
        }
        return this.save(sysTask);
    }

    /**
     * 编辑请求
     * @param sysTask 修改任务对象
     * @return 编辑任务状态
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public boolean edit(SysTask sysTask) {
        // 取消任务
        schedulingConfig.cancelTriggerTask(sysTask.getName());
        if (ConfigConsts.ENABLED_YES.equals(sysTask.getEnabled())) {
            // 添加任务
            schedulingConfig.addTriggerTask(sysTask.getName(),sysTask.getClassName(),sysTask.getMethodName(),sysTask.getCron());
        }
        return this.updateById(sysTask);
    }

    /**
     * 删除请求
     * @param id 任务id
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public void del(Long id) {
        // 查询任务
        SysTask sysTask = this.getById(id);
        // 取消任务
        schedulingConfig.cancelTriggerTask(sysTask.getName());
        // 删除任务
        this.removeById(id);
    }
}
