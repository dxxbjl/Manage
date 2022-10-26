package io.github.yangyouwang.crud.qrtz.service;

import io.github.yangyouwang.crud.qrtz.entity.JobLog;
import io.github.yangyouwang.crud.qrtz.mapper.JobLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* <p>
 * 任务日志 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-10-26
*/
@Service
public class JobLogService extends ServiceImpl<JobLogMapper, JobLog> {

  /**
  * 任务日志分页列表
  * @param param 参数
  * @return 结果
  */
  public List<JobLog> page(JobLog param) {
    QueryWrapper<JobLog> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda()
      // 任务名称
          .eq(!StringUtils.isEmpty(param.getTaskName()), JobLog::getTaskName, param.getTaskName())
          // 任务组名
          .eq(!StringUtils.isEmpty(param.getTaskGroup()), JobLog::getTaskGroup, param.getTaskGroup())
          // 调用目标字符串
          .eq(!StringUtils.isEmpty(param.getInvokeTarget()), JobLog::getInvokeTarget, param.getInvokeTarget())
          // 调用目标参数
          .eq(!StringUtils.isEmpty(param.getInvokeParam()), JobLog::getInvokeParam, param.getInvokeParam())
          // 日志信息
          .eq(!StringUtils.isEmpty(param.getTaskMessage()), JobLog::getTaskMessage, param.getTaskMessage())
          // 执行状态：0 正常、1 失败
          .eq(!StringUtils.isEmpty(param.getStatus()), JobLog::getStatus, param.getStatus())
          // 异常信息
          .eq(!StringUtils.isEmpty(param.getExceptionInfo()), JobLog::getExceptionInfo, param.getExceptionInfo())
    .orderByDesc(JobLog::getCreateTime);
    return list(queryWrapper);
  }

  /**
  * 任务日志详情
  * @param id 主键
  * @return 结果
  */
  public JobLog info(Long id) {
    return getById(id);
  }

  /**
  * 任务日志新增
  * @param param 根据需要进行传值
  */
  public void add(JobLog param) {
    save(param);
  }

  /**
  * 任务日志修改
  * @param param 根据需要进行传值
  */
  public void modify(JobLog param) {
    updateById(param);
  }

  /**
  * 任务日志删除(单个条目)
  * @param id 主键
  */
  public void remove(Long id) {
    removeById(id);
  }

  /**
  * 任务日志删除(多个条目)
  * @param ids 主键数组
  */
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
