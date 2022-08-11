package io.github.yangyouwang.crud.app.controller;

import io.github.yangyouwang.crud.app.entity.User;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.app.service.UserService;
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
* 用户表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-08-03
*/
@Api(tags = "用户表")
@Controller
@RequestMapping("/app/user")
public class UserController extends CrudController {

  private static final String SUFFIX = "/app/user";

  @Autowired
  private UserService userService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "用户表分页列表", response = User.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer")})
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(User param) {
    startPage();
    List<User> data = userService.page(param);
    return getDataTable(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = userService.info(id);
    map.put("user",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "用户表新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated User param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    userService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "用户表修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated User param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    userService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "用户表删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    userService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "用户表删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     userService.removes(ids);
     return Result.success();
   }
 }
