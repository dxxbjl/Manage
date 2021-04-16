package io.github.yangyouwang.crud.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.core.config.QuartzConfig;
import io.github.yangyouwang.crud.system.dao.SysTaskRepository;
import io.github.yangyouwang.crud.system.model.SysTask;
import io.github.yangyouwang.crud.system.model.req.SysTaskAddReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskEditReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskEnabledReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskListReq;
import io.github.yangyouwang.crud.system.model.resp.SysTaskResp;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
/**
 * @author yangyouwang
 * @title: SysTaskService
 * @projectName crud
 * @description: 任务业务层
 * @date 2021/4/10上午10:26
 */
@Service
public class SysTaskService {

    @Autowired
    private SysTaskRepository sysTaskRepository;

    @Autowired
    private QuartzConfig quartzConfig;
    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @Transactional(readOnly = true)
    public SysTaskResp detail(Long id) {
        SysTask sysTask = sysTaskRepository.findById(id).orElse(new SysTask());
        SysTaskResp sysTaskResp = new SysTaskResp();
        BeanUtils.copyProperties(sysTask,sysTaskResp);
        return sysTaskResp;
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @Transactional(readOnly = true)
    public Page<SysTask> list(SysTaskListReq sysTaskListReq) {
        Pageable pageable = PageRequest.of(sysTaskListReq.getPageNum() - 1,sysTaskListReq.getPageSize());
        Specification<SysTask> query = (Specification<SysTask>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String name = sysTaskListReq.getName();
            if(Strings.isNotBlank(name)){
                predicates.add(criteriaBuilder.like(root.get("name"),"%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return sysTaskRepository.findAll(query,pageable);
    }

    /**
     * 添加请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void add(SysTaskAddReq sysTaskAddReq) {
        SysTask sysTask = new SysTask();
        BeanUtils.copyProperties(sysTaskAddReq,sysTask);
        sysTaskRepository.save(sysTask);
        if (Constants.ENABLED_YES.equals(sysTask.getEnabled())) {
            // 添加任务
            quartzConfig.addTriggerTask(sysTask.getName(),sysTask.getClassName(),sysTask.getMethodName(),sysTask.getCron());
        }
    }

    /**
     * 编辑请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void edit(SysTaskEditReq sysTaskEditReq) {
        sysTaskRepository.findById(sysTaskEditReq.getId()).ifPresent(sysTask -> {
            BeanUtil.copyProperties(sysTaskEditReq,sysTask,true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            sysTaskRepository.save(sysTask);
            if (Constants.ENABLED_YES.equals(sysTask.getEnabled())) {
                // 添加任务
                quartzConfig.addTriggerTask(sysTask.getName(),sysTask.getClassName(),sysTask.getMethodName(),sysTask.getCron());
            }
            if (Constants.ENABLED_NO.equals(sysTask.getEnabled())) {
                // 取消任务
                quartzConfig.cancelTriggerTask(sysTask.getName());
            }
        });
    }

    /**
     * 删除请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void del(Long id) {
       sysTaskRepository.findById(id).ifPresent(sysTask -> {
           // 删除任务
           sysTaskRepository.deleteById(id);
           // 取消任务
           quartzConfig.cancelTriggerTask(sysTask.getName());
       });
    }

    /**
     * 修改任务请求
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void changeTask(SysTaskEnabledReq sysTaskEnabledReq) {
        sysTaskRepository.findById(sysTaskEnabledReq.getId()).ifPresent(sysTask -> {
            sysTask.setEnabled(sysTaskEnabledReq.getEnabled());
            sysTaskRepository.save(sysTask);
            if (Constants.ENABLED_YES.equals(sysTask.getEnabled())) {
                // 添加任务
                quartzConfig.addTriggerTask(sysTask.getName(),sysTask.getClassName(),sysTask.getMethodName(),sysTask.getCron());
            }
            if (Constants.ENABLED_NO.equals(sysTask.getEnabled())) {
                // 取消任务
                quartzConfig.cancelTriggerTask(sysTask.getName());
            }
        });
    }
}
