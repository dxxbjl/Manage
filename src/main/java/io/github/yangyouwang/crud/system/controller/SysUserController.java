package io.github.yangyouwang.crud.system.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.domain.EnabledDTO;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.crud.system.model.params.*;
import io.github.yangyouwang.crud.system.model.result.SysUserDTO;
import io.github.yangyouwang.crud.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
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
        SysUserDTO sysUser = sysUserService.findUserByName(user.getUsername());
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
        SysUserDTO sysUser = sysUserService.findUserByName(user.getUsername());
        map.put("sysUser",sysUser);
        return SUFFIX + "/modifyPass";
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
     * @param id 用户id
     * @return 编辑页面
     */
    @GetMapping("/editPage/{id}")
    public String editPage(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
        SysUserDTO sysUser = sysUserService.detail(id);
        map.put("sysUser",sysUser);
        return SUFFIX + "/edit";
    }

    /**
     * 跳转重置密码
     * @param id 用户id
     * @return 重置密码页面
     */
    @GetMapping("/resetPassPage/{id}")
    public String resetPass(@Valid @NotNull(message = "id不能为空") @PathVariable Long id, ModelMap map){
        SysUserDTO sysUser = sysUserService.detail(id);
        map.put("sysUser",sysUser);
        return SUFFIX + "/resetPass";
    }

    /**
     * 重置密码
     * @param sysUserResetPassDTO 重置用户密码对象
     * @return 重置密码状态
     */
    @CrudLog
    @PostMapping("/resetPass")
    @ResponseBody
    public Result resetPass(@RequestBody @Validated SysUserResetPassDTO sysUserResetPassDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysUserService.resetPass(sysUserResetPassDTO);
        return Result.success(flag);
    }

    /**
     * 修改密码
     * @param modifyPassDTO 修改密码对象
     * @return 修改状态
     */
    @CrudLog
    @PostMapping("/modifyPass")
    @ResponseBody
    public Result modifyPass(@RequestBody @Validated ModifyPassDTO modifyPassDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysUserService.modifyPass(modifyPassDTO);
        return Result.success(flag);
    }

    /**
     * 列表请求
     * @param sysUserListDTO 用户列表对象
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(@Validated SysUserListDTO sysUserListDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        IPage<SysUserDTO> list = sysUserService.list(sysUserListDTO);
        return Result.success(list);
    }

    /**
     * 添加请求
     * @param sysUserAddDTO 添加用户对象
     * @return 添加状态
     */
    @CrudLog
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysUserAddDTO sysUserAddDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        sysUserService.add(sysUserAddDTO);
        return Result.success();
    }

    /**
     * 编辑请求
     * @param sysUserEditDTO 编辑用户对象
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysUserEditDTO sysUserEditDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        sysUserService.edit(sysUserEditDTO);
        return Result.success();
    }

    /**
     * 编辑用户信息请求
     * @param sysUserEditUserInfoDTO 编辑用户对象
     * @return 编辑状态
     */
    @CrudLog
    @PostMapping("/editUserInfo")
    @ResponseBody
    public Result editUserInfo(@RequestBody @Validated SysUserEditUserInfoDTO sysUserEditUserInfoDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysUserService.editUserInfo(sysUserEditUserInfoDTO);
        return Result.success(flag);
    }

    /**
     * 修改用户状态
     * @param enabledDTO 修改用户状态对象
     * @return 修改状态
     */
    @CrudLog
    @PostMapping("/changeUser")
    @ResponseBody
    public Result changeUser(@RequestBody @Validated EnabledDTO enabledDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysUserService.changeUser(enabledDTO);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @param id 用户id
     * @return 删除状态
     */
    @CrudLog
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id){
        sysUserService.del(id);
        return Result.success();
    }

    /**
     * 导出用户信息
     */
    @RequestMapping("/exportExcel")
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<SysUserDTO> sysUsers= sysUserService.exportSysUserList();
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        Sheet sheet = new Sheet(1, 0, SysUserDTO.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        // 第一个 sheet 名称
        sheet.setSheetName(ConfigConsts.SYS_USER_SHEET_NAME);
        writer.write(sysUsers, sheet);
        //通知浏览器以附件的形式下载处理，设置返回头要注意文件名有中文
        response.setHeader("Content-disposition", "attachment;filename=" + new String(ConfigConsts.SYS_USER_SHEET_NAME.getBytes("gb2312"), "ISO8859-1" ) + ".xlsx");
        writer.finish();
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        out.flush();
    }
}
