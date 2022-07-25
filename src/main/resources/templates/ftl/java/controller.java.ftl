package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.PageDTO;
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

<#if restControllerStyle>
<#else>
 import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
 import ${superControllerClassPackage};
</#if>

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
/**
* <p>
* ${table.comment} 前端控制器
* </p>
* @author ${author}
* @since ${date}
*/
@Api(tags = "${table.comment}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

  private static final String SUFFIX = "<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>";

  @Autowired
  private ${table.serviceName} ${table.serviceName?uncap_first};

  /**
  * 跳转列表
  * @return 列表页面
  */
  @GetMapping("/listPage")
  public String listPage(){
    return SUFFIX + "/list";
  }

  @ApiOperation(value = "${table.comment}分页列表", response = ${entity}.class)
  @ApiImplicitParams({
  @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer"),
  @ApiImplicitParam(name = "pageSize", value = "每页记录数", dataType = "Integer")})
  @GetMapping(value = "/page")
  @ResponseBody
  public Result page(@Validated PageDTO pageDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    Object data = ${table.serviceName?uncap_first}.page(pageDTO);
    return Result.success(data);
  }

   /**
   * 跳转编辑
   * @param id 主键
   * @return 编辑页面
   */
   @GetMapping("/editPage/{id}")
   public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
    Object data = ${table.serviceName?uncap_first}.info(id);
    map.put("<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>",data);
    return SUFFIX + "/edit";
  }

  /**
  * 跳转添加
  * @return 添加页面
  */
  @GetMapping("/addPage")
  public String addPage(){
    return SUFFIX + "/add";
  }

  @ApiOperation(value = "${table.comment}新增")
  @PostMapping(value = "/add")
  @ResponseBody
  public Result add(@RequestBody @Validated ${entity} param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    ${table.serviceName?uncap_first}.add(param);
    return Result.success();
  }

  @ApiOperation(value = "${table.comment}修改")
  @PostMapping(value = "/modify")
  @ResponseBody
  public Result modify(@RequestBody @Validated ${entity} param,BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    ${table.serviceName?uncap_first}.modify(param);
    return Result.success();
  }

  @ApiOperation(value = "${table.comment}删除(单个条目)")
  @DeleteMapping(value = "/remove/{id}")
  @ResponseBody
  public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
    ${table.serviceName?uncap_first}.remove(id);
    return Result.success();
  }

  @ApiOperation(value = "${table.comment}删除(多个条目)")
  @PostMapping(value = "/removes")
  @ResponseBody
  public Result removes(@RequestBody @Valid List<Long> ids) {
     ${table.serviceName?uncap_first}.removes(ids);
     return Result.success();
   }
 }
 </#if>