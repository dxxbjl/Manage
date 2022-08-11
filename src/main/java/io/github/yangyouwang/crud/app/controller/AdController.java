package io.github.yangyouwang.crud.app.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.crud.app.entity.Ad;
import io.github.yangyouwang.crud.app.service.AdService;
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
* 广告表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-08-11
*/
@Api(tags = "广告表")
@Controller
@RequestMapping("/app/ad")
public class AdController extends CrudController {

  private static final String SUFFIX = "/app/ad";

  @Autowired
  private AdService adService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "广告表分页列表", response = Ad.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer")})
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
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    adService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "广告表删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     adService.removes(ids);
     return Result.success();
   }

    /**
     * 修改轮播图状态
     * @param param 修改轮播图参数
     * @return 修改状态
     */
    @CrudLog
    @PostMapping("/changeAd")
    @ResponseBody
    public Result changeAd(@RequestBody @Validated Ad param, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = adService.updateById(param);
        return Result.success(flag);
    }
 }
