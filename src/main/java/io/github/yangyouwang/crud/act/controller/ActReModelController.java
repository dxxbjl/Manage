package io.github.yangyouwang.crud.act.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.crud.act.entity.ActReModel;
import io.github.yangyouwang.crud.act.model.req.ActReModelAddReq;
import io.github.yangyouwang.crud.act.model.req.ActReModelEditReq;
import io.github.yangyouwang.crud.act.model.req.ActReModelListReq;
import io.github.yangyouwang.crud.act.model.resp.ActReModelResp;
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
        ActReModelResp actReModel = actReModelService.detail(id);
        map.put("actReModel",actReModel);
        return SUFFIX + "/edit";
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Valid ActReModelListReq actReModelListReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<ActReModelResp> list = actReModelService.list(actReModelListReq);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Valid ActReModelAddReq actReModelAddReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        String flag = actReModelService.add(actReModelAddReq);
        return Result.success(flag);
    }

    /**
     * 设计流程模型
     */
    @GetMapping("/design/{id}")
    public String design(@Valid @NotNull(message = "id不能为空") @PathVariable("id") String id) {
        return "redirect:/static/modeler.html?modelId=" + id;
    }


    /**
     * 部署流程模型
     */
    @GetMapping("/deploy/{id}")
    @ResponseBody
    public Result deploy(@Valid @NotNull(message = "id不能为空")  @PathVariable("id") String id) {
        String flag = actReModelService.deploy(id);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Valid ActReModelEditReq actReModelEditReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = actReModelService.edit(actReModelEditReq);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable String id){
        int flag = actReModelService.del(id);
        return Result.success(flag);
    }
}
