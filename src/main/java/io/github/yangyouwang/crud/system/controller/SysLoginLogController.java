package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.crud.system.entity.SysLoginLog;
import io.github.yangyouwang.crud.system.service.SysLoginLogService;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

 import org.springframework.stereotype.Controller;
 import io.github.yangyouwang.common.base.CrudController;

import java.util.List;
/**
* <p>
* 用户登录日志记录表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-08-29
*/
@Api(tags = "用户登录日志记录表")
@Controller
@RequestMapping("/sysLoginLog")
public class SysLoginLogController extends CrudController {

  private static final String SUFFIX = "/system/sysLoginLog";

  @Autowired
  private SysLoginLogService sysLoginLogService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "用户登录日志记录表分页列表", response = SysLoginLog.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer")})
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(SysLoginLog param) {
    startPage();
    List<SysLoginLog> data = sysLoginLogService.page(param);
    return getDataTable(data);
  }
}
