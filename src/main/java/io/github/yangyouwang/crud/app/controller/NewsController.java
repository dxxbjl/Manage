package io.github.yangyouwang.crud.app.controller;

import io.github.yangyouwang.crud.app.entity.News;
import io.github.yangyouwang.crud.app.service.NewsService;
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
* 新闻表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-11-01
*/
@Api(tags = "新闻表")
@Controller
@RequestMapping("/app/news")
public class NewsController extends CrudController {

  private static final String SUFFIX = "app/news";

  @Autowired
  private NewsService newsService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "新闻表分页列表", response = News.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer", paramType="query"),
          @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer", paramType="query")
  })
  @PreAuthorize("hasAuthority('news:list')")
  @GetMapping(value = "/page")
  @ResponseBody
  public TableDataInfo page(News param) {
    startPage();
    List<News> data = newsService.page(param);
    return getDataTable(data);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = newsService.info(id);
    map.put("news",data);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "新闻表新增")
  @PreAuthorize("hasAuthority('news:add')")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated News param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    newsService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "新闻表修改")
  @PreAuthorize("hasAuthority('news:edit')")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated News param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    newsService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "新闻表删除(单个条目)")
  @PreAuthorize("hasAuthority('news:del')")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    newsService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "新闻表删除(多个条目)")
  @PreAuthorize("hasAuthority('news:del')")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     newsService.removes(ids);
     return Result.success();
   }
}
