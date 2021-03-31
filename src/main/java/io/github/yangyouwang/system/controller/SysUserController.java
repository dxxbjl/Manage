package io.github.yangyouwang.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.system.model.req.ModifyPassReq;
import io.github.yangyouwang.system.model.req.SysUserAddReq;
import io.github.yangyouwang.system.model.req.SysUserEditReq;
import io.github.yangyouwang.system.model.req.SysUserListReq;
import io.github.yangyouwang.system.model.resp.SysUserResp;
import io.github.yangyouwang.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yangyouwang
 * @title: SysUserController
 * @projectName crud
 * @description: 用户控制层
 * @date 2021/3/2112:38 AM
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {

    private static final String SUFFIX = "/system/sysUser";

    @Autowired
    private SysUserService sysUserService;

    /**
     * 跳转到用户信息
     * @return 用户信息页面
     */
    @GetMapping("/userInfoPage")
    public String userInfoPage(ModelMap map){
        SysUserResp sysUser = sysUserService.detail(SecurityUtils.getSysUser().getId());
        map.put("sysUser",sysUser);
        return SUFFIX + "/userInfo";
    }

    /**
     * 跳转到修改密码
     * @return 修改密码页面
     */
    @GetMapping("/modifyPassPage")
    public String modifyPassPage(ModelMap map) {
        SysUserResp sysUser = sysUserService.detail(SecurityUtils.getSysUser().getId());
        map.put("sysUser",sysUser);
        return SUFFIX + "/password";
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
    public String editPage(@PathVariable Long id, ModelMap map){
        SysUserResp sysUser = sysUserService.detail(id);
        map.put("sysUser",sysUser);
        return SUFFIX + "/edit";
    }


    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(SysUserListReq sysUserListReq){
        return Result.success(sysUserService.list(sysUserListReq));
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Valid SysUserAddReq sysUserAddReq,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysUserService.add(sysUserAddReq);
        return Result.success();
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Valid SysUserEditReq sysUserEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysUserService.edit(sysUserEditReq);
        return Result.success();
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@PathVariable Long id){
        sysUserService.del(id);
        return Result.success();
    }

    /**
     * 修改密码
     * @return 修改状态
     */
    @PostMapping("/modifyPass")
    @ResponseBody
    public Result modifyPass(@RequestBody @Valid ModifyPassReq modifyPassReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        sysUserService.modifyPass(modifyPassReq);
        return Result.success();
    }
}
