package io.github.yangyouwang.crud.system.service;

import io.github.yangyouwang.crud.system.dao.SysTaskRepository;
import io.github.yangyouwang.crud.system.model.SysTask;
import io.github.yangyouwang.crud.system.model.req.SysTaskAddReq;
import io.github.yangyouwang.crud.system.model.req.SysTaskEditReq;
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

    /**
     * 跳转编辑
     * @return 编辑页面
     */
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
     * @return 添加状态
     */
    public void add(SysTaskAddReq sysTaskAddReq) {
        SysTask sysTask = new SysTask();
        BeanUtils.copyProperties(sysTaskAddReq,sysTask);
        sysTaskRepository.save(sysTask);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    public void edit(SysTaskEditReq sysTaskEditReq) {
        SysTask sysTask = sysTaskRepository.findById(sysTaskEditReq.getId()).get();
        BeanUtils.copyProperties(sysTaskEditReq,sysTask);
        sysTaskRepository.save(sysTask);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    public void del(Long id) {
        if(sysTaskRepository.existsById(id)) {
            sysTaskRepository.deleteById(id);
        }
    }
}
