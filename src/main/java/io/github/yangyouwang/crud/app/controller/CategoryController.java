package io.github.yangyouwang.crud.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.crud.app.entity.Category;
import io.github.yangyouwang.crud.app.service.CategoryService;
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
* 分类表 前端控制器
* </p>
* @author yangyouwang
* @since 2022-10-30
*/
@Api(tags = "分类表")
@Controller
@RequestMapping("/app/category")
public class CategoryController extends CrudController {

  private static final String SUFFIX = "app/category";

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "分类表分页列表", response = Category.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer", paramType="query"),
          @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer", paramType="query")
  })
  @GetMapping(value = "/page")
  @ResponseBody
  public Result page() {
    List<Category> list = categoryService.list(new LambdaQueryWrapper<Category>()
            .orderByAsc(Category::getParentId,Category::getOrderNum));
    return Result.success(list);
  }

   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Category category = categoryService.info(id);
    map.put("category",category);
    return SUFFIX + "/edit";
  }

  @GetMapping("/addPage")
  public String addPage(Category category, ModelMap map) {
    map.put("parentId",category.getId());
    map.put("parentName",category.getName());
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "分类表新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated Category param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    categoryService.add(param);
    return Result.success();
  }

  @ApiOperation(value = "分类表修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated Category param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    categoryService.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "分类表删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    categoryService.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "分类表删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     categoryService.removes(ids);
     return Result.success();
   }

  /**
   * 查询分类树结构
   * @return 分类树结构
   */
  @GetMapping("/treeSelect")
  @ResponseBody
  public List<TreeSelectNode> treeSelect() {
    return categoryService.treeSelect();
  }
}
