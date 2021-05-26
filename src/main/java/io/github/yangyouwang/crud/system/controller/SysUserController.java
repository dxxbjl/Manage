package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.crud.system.entity.SysUser;
import io.github.yangyouwang.crud.system.model.req.*;
import io.github.yangyouwang.crud.system.model.resp.SysUserResp;
import io.github.yangyouwang.crud.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;


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

    private static final String SUFFIX = "system/sysUser";

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 跳转到用户信息
     * @return 用户信息页面
     */
    @GetMapping("/userInfoPage")
    public String userInfoPage(ModelMap map){
        User user = SecurityUtils.getSysUser();
        SysUserResp sysUser = sysUserService.findUserByName(user.getUsername());
        map.put("sysUser",sysUser);
        return SUFFIX + "/userInfo";
    }

    /**
     * 跳转到修改密码
     * @return 修改密码页面
     */
    @GetMapping("/modifyPassPage")
    public String modifyPassPage(ModelMap map) {
        User user = SecurityUtils.getSysUser();
        SysUserResp sysUser = sysUserService.findUserByName(user.getUsername());
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
    public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
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
    public Result list(@Validated SysUserListReq sysUserListReq,BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysUserResp> list = sysUserService.list(sysUserListReq);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @return 添加状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysUserAddReq sysUserAddReq,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysUserService.add(sysUserAddReq);
        return Result.success(flag);
    }

    /**
     * 编辑请求
     * @return 编辑状态
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysUserEditReq sysUserEditReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysUserService.edit(sysUserEditReq);
        return Result.success(flag);
    }

    /**
     * 编辑用户信息请求
     * @return 编辑状态
     */
    @PostMapping("/editUserInfo")
    @ResponseBody
    public Result editUserInfo(@RequestBody @Validated SysUserEditUserInfoReq sysUserEditUserInfoReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysUserService.editUserInfo(sysUserEditUserInfoReq);
        return Result.success(flag);
    }

    /**
     * 修改用户状态
     * @return 修改状态
     */
    @PostMapping("/changeUser")
    @ResponseBody
    public Result changeUser(@RequestBody @Validated SysUserEnabledReq sysUserEnabledReq, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysUserService.changeUser(sysUserEnabledReq);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @return 删除状态
     */
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        int flag = sysUserService.del(id);
        return Result.success(flag);
    }

    /**
     * 修改密码
     * @return 修改状态
     */
    @PostMapping("/modifyPass")
    @ResponseBody
    public Result modifyPass(@RequestBody @Validated ModifyPassReq modifyPassReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int flag = sysUserService.modifyPass(modifyPassReq);
        return Result.success(flag);
    }
}
