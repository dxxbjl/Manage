package io.github.yangyouwang.crud.act.controller;

import io.github.yangyouwang.crud.act.entity.FormData;
import io.github.yangyouwang.crud.act.service.FormDataService;
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
* 表单 前端控制器
* </p>
* @author yangyouwang
* @since 2022-08-31
*/
@Api(tags = "表单")
@Controller
@RequestMapping("/act/formData")
public class FormDataController extends CrudController {

  private static final String SUFFIX = "act/formData";

  @Autowired
  private FormDataService formDataService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "表单分页列表", response = FormData.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer", paramType="query"),
          @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer", paramType="query")
  })
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(FormData param) {
    startPage();
    List<FormData> data = formDataService.page(param);
    return getDataTable(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = formDataService.info(id);
    map.put("formData",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "表单新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated FormData param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    formDataService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "表单修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated FormData param, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    formDataService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "表单删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    formDataService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "表单删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
    formDataService.removes(ids);
     return Result.success();
   }
}
