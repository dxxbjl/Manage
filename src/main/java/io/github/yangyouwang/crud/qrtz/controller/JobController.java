package io.github.yangyouwang.crud.qrtz.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.common.enums.BusinessType;
import io.github.yangyouwang.crud.qrtz.entity.QrtzJob;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.qrtz.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;

 import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
/**
* <p>
* 任务表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-07-30
*/
@Api(tags = "任务表")
@Controller
@RequestMapping("/qrtz/job")
@RequiredArgsConstructor
public class JobController extends CrudController {

  private static final String SUFFIX = "qrtz/job";

  private final JobService jobService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @PreAuthorize("hasAuthority('job:list')")
  @ApiOperation(value = "任务表分页列表", response = QrtzJob.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer")})
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(@Validated QrtzJob job) {
    startPage();
    List<QrtzJob> data = jobService.page(job);
    return getDataTable(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = jobService.getById(id);
    map.put("job",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }


  @CrudLog(title = "新增任务",businessType = BusinessType.INSERT)
  @ApiOperation(value = "任务新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated QrtzJob param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
      jobService.add(param);
    return Result.success();
  }
  @CrudLog(title = "修改任务",businessType = BusinessType.UPDATE)
  @ApiOperation(value = "任务表修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated QrtzJob param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    jobService.modify(param);
    return Result.success();
  }
  @CrudLog(title = "删除任务",businessType = BusinessType.DELETE)
  @ApiOperation(value = "任务表删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    jobService.remove(id);
    return Result.success();
  }

  @CrudLog(title = "删除任务",businessType = BusinessType.DELETE)
  @ApiOperation(value = "任务表删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     jobService.removes(ids);
     return Result.success();
   }

   @GetMapping("/cronPage")
   public String cronPage(){
        return "qrtz/cron/index";
    }

    @CrudLog(title = "暂停任务",businessType = BusinessType.UPDATE)
    @ApiOperation(value = "暂停某个定时任务")
    @GetMapping(value = "/pause/{id}")
    @ResponseBody
    public Result pause(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
        jobService.pauseJob(id);
        return Result.success();
    }
    @CrudLog(title = "恢复任务",businessType = BusinessType.UPDATE)
    @ApiOperation(value = "恢复某个定时任务")
    @GetMapping(value = "/resume/{id}")
    @ResponseBody
    public Result resume(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
        jobService.resumeJob(id);
        return Result.success();
    }
}
