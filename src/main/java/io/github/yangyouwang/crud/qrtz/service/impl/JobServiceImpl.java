package io.github.yangyouwang.crud.qrtz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.crud.qrtz.entity.Job;
import io.github.yangyouwang.crud.qrtz.mapper.JobMapper;
import io.github.yangyouwang.crud.qrtz.model.params.JobEditDTO;
import io.github.yangyouwang.crud.qrtz.model.params.JobPageDTO;
import io.github.yangyouwang.crud.qrtz.model.params.JobAddDTO;
import io.github.yangyouwang.crud.qrtz.model.result.JobDTO;
import io.github.yangyouwang.crud.qrtz.service.IJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* <p>
 * 任务表 服务实现类
 * </p>
*
* @author yangyouwang
* @since 2022-07-30
*/
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

  @Autowired
  private Scheduler scheduler;

  /**
  * 任务表分页列表
  * @param pageDTO 分页DTO
  * @return 结果
  */
  @Override
  public IPage<Job> page(JobPageDTO pageDTO) {
    LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(StringUtils.isNotBlank(pageDTO.getJobName()),Job::getJobName,pageDTO.getJobName());
    return page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
  }

  /**
  * 任务表详情
  * @param id 主键
  * @return 结果
  */
  @Override
  public JobDTO info(Long id) {
    Job job = getById(id);
    JobDTO jobDTO = new JobDTO();
    BeanUtils.copyProperties(job,jobDTO);
    return jobDTO;
  }

  /**
  * 任务表新增
  * @param param 根据需要进行传值
  */
  @Override
  public void add(JobAddDTO param) {
    try {
      /**通过JobBuilder.newJob()方法获取到当前Job的具体实现(以下均为链式调用)
       * 这里是固定Job创建，所以代码写死XXX.class
       * 如果是动态的，根据不同的类来创建Job，则 ((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass()
       * 即是 JobBuilder.newJob(((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass())
       * */
      JobDetail jobDetail = JobBuilder.newJob(((org.quartz.Job)Class.forName(param.getJobClassName()).newInstance()).getClass())
              /**给当前JobDetail添加参数，K V形式*/
              //.usingJobData("name","zy")
              /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getJobDetail().getJobDataMap().get("age")获取值*/
              //  .usingJobData("age",23)
              /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
              .withIdentity(param.getJobName())
              .build();//执行

      Trigger trigger = TriggerBuilder.newTrigger()
              /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getTrigger().getJobDataMap().get("orderNo")获取值*/
              // .usingJobData("orderNo", orderNo)
              /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
              .withIdentity(param.getJobName())
              /**立即生效*/
              .startNow()
              /**开始执行时间*/
//         .startAt(start)
              /**结束执行时间*/
//        .endAt(start)
              /**添加执行规则，SimpleTrigger、CronTrigger的区别主要就在这里*/
              .withSchedule(
                      CronScheduleBuilder.cronSchedule(param.getCron())
              )
              .build();//执行

      /**添加定时任务*/
      scheduler.scheduleJob(jobDetail, trigger);
      if (!scheduler.isShutdown()) {
        /**启动*/
        scheduler.start();
      }
      System.err.println("--------定时任务启动成功 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" ------------");
    } catch (ClassNotFoundException | SchedulerException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(String.format("创建定时任务出错:%s",e));
    }
    Job job = new Job();
    BeanUtils.copyProperties(param,job);
    save(job);
  }

  /**
  * 任务表修改
  * @param param 根据需要进行传值
  */
  @Override
  public void modify(JobEditDTO param) {
    try {
      //获取到对应任务的触发器
      TriggerKey triggerKey = TriggerKey.triggerKey(param.getJobName());
      //设置定时任务执行方式
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(param.getCron());
      //重新构建任务的触发器trigger
      CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
      trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
      //重置对应的job
      scheduler.rescheduleJob(triggerKey, trigger);
    } catch (Exception e) {
      throw new RuntimeException(String.format("修改定时任务出错:%s",e));
    }
    Job job = new Job();
    BeanUtils.copyProperties(param,job);
    updateById(job);
  }

  /**
  * 任务表删除(单个条目)
  * @param id 主键
  */
  @Override
  public void remove(Long id) {
    Job job = getById(id);
    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName());
      // 停止触发器
      scheduler.pauseTrigger(triggerKey);
      // 移除触发器
      scheduler.unscheduleJob(triggerKey);
      // 删除任务
      scheduler.deleteJob(JobKey.jobKey(job.getJobName()));
    } catch (Exception e) {
      throw new RuntimeException(String.format("删除定时任务出错:%s",e));
    }
    removeById(id);
  }

  /**
  * 任务表删除(多个条目)
  * @param ids 主键数组
  */
  @Override
  public void removes(List<Long> ids) {
     removeByIds(ids);
   }
}
