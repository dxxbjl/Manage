package io.github.yangyouwang.crud.act.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.act.model.params.ActReModelAddDTO;
import io.github.yangyouwang.crud.act.model.params.ActReModelEditDTO;
import io.github.yangyouwang.crud.act.model.params.ActReModelListDTO;
import io.github.yangyouwang.crud.act.model.result.ActReModelDTO;
import io.github.yangyouwang.crud.act.service.ActReModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
public class ActReModelController {

    private String SUFFIX = "act/model";

    private final ActReModelService actReModelService;

    @Autowired
    public ActReModelController(ActReModelService actReModelService) {
        this.actReModelService = actReModelService;
    }

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
        ActReModelDTO actReModel = actReModelService.detail(id);
        map.put("actReModel",actReModel);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @param actReModelListDTO 模型列表对象
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Valid ActReModelListDTO actReModelListDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<ActReModelDTO> list = actReModelService.list(actReModelListDTO);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @param actReModelAddDTO 模型添加对象
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Valid ActReModelAddDTO actReModelAddDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String flag = actReModelService.add(actReModelAddDTO);
        return Result.success(flag);
    }

    /**
     * 设计流程模型
     * @param id 模型id
     * @return 模型设计页面
     */
    @GetMapping("/design/{id}")
    public String design(@Valid @NotNull(message = "id不能为空") @PathVariable("id") String id) {
        return "redirect:/static/modeler.html?modelId=" + id;
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
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @param actReModelEditDTO 模型编辑对象
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Valid ActReModelEditDTO actReModelEditDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = actReModelService.edit(actReModelEditDTO);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @param id 模型id
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable String id){
        actReModelService.removeById(id);
        return Result.success();
    }
}
