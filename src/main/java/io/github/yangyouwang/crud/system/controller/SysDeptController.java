package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.common.enums.BusinessType;
import io.github.yangyouwang.crud.system.entity.SysDept;
import io.github.yangyouwang.crud.system.service.SysDeptService;
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
import io.github.yangyouwang.common.base.CrudController;

import javax.validation.Valid;
import java.util.Objects;
import java.util.List;
/**
* <p>
* 部门表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-09-03
*/
@Api(tags = "部门表")
@Controller
@RequestMapping("/system/sysDept")
public class SysDeptController extends CrudController {

  private static final String SUFFIX = "system/sysDept";

  @Autowired
  private SysDeptService sysDeptService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "部门表分页列表", response = SysDept.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer")})
  @GetMapping(value = "/page")
  @ResponseBody
  public Result page(SysDept param) {
    List<SysDept> data = sysDeptService.page(param);
    return Result.success(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = sysDeptService.info(id);
    map.put("sysDept",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(SysDept sysDept, ModelMap map) {
    if (Objects.nonNull(sysDept)) {
      map.put("parentId",sysDept.getId());
      map.put("parentName",sysDept.getDeptName());
    }
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "部门表新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated SysDept param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    sysDeptService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "部门表修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated SysDept param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    sysDeptService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "部门表删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    sysDeptService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "部门表删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     sysDeptService.removes(ids);
     return Result.success();
   }

  /**
   * 查询部门树结构
   * @return 部门树结构
   */
  @GetMapping("/treeSelect")
  @ResponseBody
  public List<TreeSelectNode> treeSelect() {
    return sysDeptService.treeSelect();
  }

  /**
   * 更新部门状态
   * @param sysDept 修改部门状态对象
   * @return 修改状态
   */
  @CrudLog(title = "更新部门状态",businessType = BusinessType.UPDATE)
  @PostMapping("/changeDept")
  @ResponseBody
  public Result changeDept(@RequestBody @Validated SysDept sysDept, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
      return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    boolean flag = sysDeptService.updateById(sysDept);
    return Result.success(flag);
  }
}
