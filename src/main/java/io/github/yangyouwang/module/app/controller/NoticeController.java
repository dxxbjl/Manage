package io.github.yangyouwang.module.app.controller;

import io.github.yangyouwang.module.app.entity.Notice;
import io.github.yangyouwang.module.app.service.NoticeService;
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
* 通知公告表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-10-04
*/
@Api(tags = "通知公告表")
@Controller
@RequestMapping("/app/notice")
public class NoticeController extends CrudController {

  private static final String SUFFIX = "app/notice";

  @Autowired
  private NoticeService noticeService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "通知公告表分页列表", response = Notice.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer", paramType="query"),
          @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer", paramType="query")
  })
  @PreAuthorize("hasAuthority('notice:list')")
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(Notice param) {
    startPage();
    List<Notice> data = noticeService.page(param);
    return getDataTable(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = noticeService.info(id);
    map.put("notice",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "通知公告表新增")
  @PreAuthorize("hasAuthority('notice:add')")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated Notice param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    noticeService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "通知公告表修改")
  @PreAuthorize("hasAuthority('notice:edit')")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated Notice param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    noticeService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "通知公告表删除(单个条目)")
  @PreAuthorize("hasAuthority('notice:del')")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    noticeService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "通知公告表删除(多个条目)")
  @PreAuthorize("hasAuthority('notice:del')")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     noticeService.removes(ids);
     return Result.success();
   }
}
