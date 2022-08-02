package io.github.yangyouwang.crud.qrtz.service;

import io.github.yangyouwang.crud.qrtz.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
* <p>
 * 任务表 服务类
 * </p>
*
* @author yangyouwang
* @since 2022-07-30
*/
  public interface IJobService extends IService<Job> {

  /**
  * 任务表分页列表
  * @param job 分页DTO
  * @return 结果
  */
  List<Job> page(Job job);

  /**
  * 任务表新增
  * @param param 根据需要进行传值
  */
  void add(Job param);

  /**
  * 任务表修改
  * @param param 根据需要进行传值
  */
  void modify(Job param);

  /**
  * 任务表删除(单个条目)
  * @param id 主键
  */
  void remove(Long id);

  /**
  * 删除(多个条目)
  * @param ids 主键数组
  */
  void removes(List<Long> ids);
}
