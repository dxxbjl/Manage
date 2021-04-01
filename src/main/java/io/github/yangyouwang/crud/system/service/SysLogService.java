package io.github.yangyouwang.crud.system.service;

import io.github.yangyouwang.crud.system.dao.SysLogRepository;
import io.github.yangyouwang.crud.system.model.SysLog;
import io.github.yangyouwang.crud.system.model.req.SysLogListReq;
import org.apache.logging.log4j.util.Strings;
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
 * @title: SysLogService
 * @projectName crud
 * @description: 系统日志业务层
 * @date 2021/4/111:08 AM
 */
@Service
public class SysLogService {

    @Autowired
    private SysLogRepository sysLogRepository;

    /**
     * 列表请求
     * @return 请求列表
     */
    public Page<SysLog> list(SysLogListReq sysLogListReq) {
        Pageable pageable = PageRequest.of(sysLogListReq.getPageNum() - 1,sysLogListReq.getPageSize());
        Specification<SysLog> query = (Specification<SysLog>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String className = sysLogListReq.getClassName();
            if(Strings.isNotBlank(className)){
                predicates.add(criteriaBuilder.like(root.get("className"),"%" + className + "%"));
            }
            String methodName = sysLogListReq.getMethodName();
            if(Strings.isNotBlank(methodName)){
                predicates.add(criteriaBuilder.like(root.get("methodName"),"%" +methodName + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
       return sysLogRepository.findAll(query,pageable);
    }
}
