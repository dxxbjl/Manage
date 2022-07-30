package io.github.yangyouwang.crud.qrtz.controller;

import io.github.yangyouwang.crud.qrtz.entity.Job;
import io.github.yangyouwang.crud.qrtz.model.params.JobEditDTO;
import io.github.yangyouwang.crud.qrtz.model.params.JobPageDTO;
import io.github.yangyouwang.crud.qrtz.model.params.JobAddDTO;
import io.github.yangyouwang.crud.qrtz.service.IJobService;
import io.github.yangyouwang.common.domain.Result;
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
public class JobController {

  private static final String SUFFIX = "/qrtz/job";

  @Autowired
  private IJobService iJobService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "任务表分页列表", response = Job.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "pageSize", value = "每页记录数", dataType = "Integer")})
  @GetMapping(value = "/page")
  @ResponseBody
  public Result page(@Validated JobPageDTO pageDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    Object data = iJobService.page(pageDTO);
    return Result.success(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = iJobService.info(id);
    map.put("job",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "任务表新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated JobAddDTO param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    iJobService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "任务表修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated JobEditDTO param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    iJobService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "任务表删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    iJobService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "任务表删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     iJobService.removes(ids);
     return Result.success();
   }

   @GetMapping("/cronPage")
   public String cronPage(){
        return "qrtz/cron/index";
    }
}
