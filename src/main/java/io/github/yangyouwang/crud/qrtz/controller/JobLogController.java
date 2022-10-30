package io.github.yangyouwang.crud.qrtz.controller;

import io.github.yangyouwang.crud.qrtz.entity.QrtzJobLog;
import io.github.yangyouwang.crud.qrtz.service.JobLogService;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;

import org.springframework.stereotype.Controller;
import io.github.yangyouwang.common.base.CrudController;

import javax.validation.Valid;
import java.util.Objects;
import java.util.List;
/**
* <p>
* 任务日志 前端控制器
* </p>
* @author yangyouwang
* @since 2022-10-26
*/
@Api(tags = "任务日志")
@Controller
@RequestMapping("/qrtz/jobLog")
public class JobLogController extends CrudController {

  private static final String SUFFIX = "qrtz/jobLog";

  @Autowired
  private JobLogService jobLogService;

  @GetMapping("/listPage")
  public String listPage(String jobId, ModelMap map) {
    map.put("jobId",jobId);
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "任务日志分页列表", response = QrtzJobLog.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer")})
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(QrtzJobLog param) {
    startPage();
    List<QrtzJobLog> data = jobLogService.page(param);
    return getDataTable(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = jobLogService.info(id);
    map.put("jobLog",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "任务日志新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated QrtzJobLog param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    jobLogService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "任务日志修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated QrtzJobLog param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    jobLogService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "任务日志删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    jobLogService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "任务日志删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     jobLogService.removes(ids);
     return Result.success();
   }
}
