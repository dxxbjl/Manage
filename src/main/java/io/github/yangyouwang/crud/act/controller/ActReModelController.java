package io.github.yangyouwang.crud.act.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.crud.act.entity.ActReModel;
import io.github.yangyouwang.crud.act.service.ActReModelService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author yangyouwang
 * @title: ActReModelController
 * @projectName crud
 * @description: 流程模型控制层
 * @date 2021/4/10下午2:03
 */
@Controller
@RequestMapping("/actReModel")
@RequiredArgsConstructor
public class ActReModelController extends CrudController {

    private static final String SUFFIX = "act/reModel";

    private final ActReModelService actReModelService;

    /**
     * 跳转列表
     * @return 列表页面
     */
    @GetMapping("/listPage")
    public String listPage(){
        return SUFFIX + "/list";
    }

    /**
     * 跳转添加
     * @return 添加页面
     */
    @GetMapping("/addPage")
    public String addPage(){
        return SUFFIX + "/add";
    }

    /**
     * 跳转编辑
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@Valid @NotNull(message = "id不能为空")  @PathVariable String id, ModelMap map){
        ActReModel actReModel = actReModelService.getById(id);
        JSONObject jsonObject = JSONObject.parseObject(actReModel.getMetaInfo());
        actReModel.setDescription(jsonObject.getString("description"));
        map.put("actReModel",actReModel);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @param actReModel 模型列表对象
     * @return 请求列表
     */
    @PreAuthorize("hasAuthority('model:list')")
    @GetMapping("/page")
    @ResponseBody
    public TableDataInfo page(ActReModel actReModel) {
        startPage();
        List<ActReModel> list = actReModelService.list(actReModel);
        return getDataTable(list);
    }

    /**
     * 添加请求
     * @param actReModel 模型添加对象
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Valid ActReModel actReModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String flag = actReModelService.add(actReModel);
        return Result.success(flag);
    }

    /**
     * 设计流程模型
     * @param id 模型id
     * @return 模型设计页面
     */
    @GetMapping("/design/{id}")
    public String design(@Valid @NotNull(message = "id不能为空") @PathVariable("id") String id) {
        return "redirect:/static/act/modeler.html?modelId=" + id;
    }

    /**
     * 部署流程模型
     * @param id 模型id
     * @return 是否部署成功响应
     */
    @GetMapping("/deploy/{id}")
    @ResponseBody
    public Result deploy(@Valid @NotNull(message = "id不能为空")  @PathVariable("id") String id) {
        String flag = actReModelService.deploy(id);
        return Result.success(String.format("流程部署成功，部署ID：%S",flag));
    }

    /**
     * 编辑请求
     * @param actReModel 模型编辑对象
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Valid ActReModel actReModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        actReModelService.edit(actReModel);
        return Result.success();
    }

    /**
     * 模型删除
     * @param id 模型id
     * @return 删除状态
     */
    @ApiOperation(value = "模型删除(单个条目)")
    @DeleteMapping(value = "/remove/{id}")
    @ResponseBody
    public Result remove(@Valid @NotNull(message = "id不能为空") @PathVariable String id) {
        actReModelService.remove(id);
        return Result.success();
    }
}
