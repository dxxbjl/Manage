package io.github.yangyouwang.crud.app.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.enums.BusinessType;
import io.github.yangyouwang.crud.app.entity.Ad;
import io.github.yangyouwang.crud.app.service.AdService;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
* 广告表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-08-11
*/
@Api(tags = "广告表")
@Controller
@RequestMapping("/app/ad")
public class AdController extends CrudController {

  private static final String SUFFIX = "app/ad";

  @Autowired
  private AdService adService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @PreAuthorize("hasAuthority('ad:list')")
  @ApiOperation(value = "广告表分页列表", response = Ad.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer", paramType="query"),
          @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer", paramType="query")
  })
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(Ad param) {
    startPage();
    List<Ad> data = adService.page(param);
    return getDataTable(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = adService.info(id);
    map.put("ad",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "广告表新增")
  @PostMapping(value = "/add")
  @ResponseBody
  @CrudLog(title = "新增广告",businessType = BusinessType.INSERT)
  public Result add(@RequestBody @Validated Ad param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    adService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "广告表修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  @CrudLog(title = "修改广告",businessType = BusinessType.UPDATE)
  public Result modify(@RequestBody @Validated Ad param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    adService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "广告表删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  @CrudLog(title = "删除广告",businessType = BusinessType.DELETE)
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    adService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "广告表删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  @CrudLog(title = "删除广告",businessType = BusinessType.DELETE)
  public Result removes(@RequestBody @Valid List<Long> ids) {
     adService.removes(ids);
     return Result.success();
   }
 }
