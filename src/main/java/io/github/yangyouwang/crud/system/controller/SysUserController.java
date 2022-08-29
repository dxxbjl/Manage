package io.github.yangyouwang.crud.system.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.common.enums.BusinessType;
import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.crud.system.entity.SysUser;
import io.github.yangyouwang.crud.system.model.ModifyPassDTO;
import io.github.yangyouwang.crud.system.model.ResetPassDTO;
import io.github.yangyouwang.crud.system.model.SysUserDTO;
import io.github.yangyouwang.crud.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
@RequiredArgsConstructor
public class SysUserController extends CrudController {

    private static final String SUFFIX = "system/sysUser";

    private final SysUserService sysUserService;

    /**
     * 跳转到用户信息
     * @return 用户信息页面
     */
    @GetMapping("/userInfoPage")
    public String userInfoPage(ModelMap map){
        String userName = SecurityUtils.getUserName();
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName,userName));
        map.put("sysUser",sysUser);
        return SUFFIX + "/userInfo";
    }

    /**
     * 跳转到修改密码
     * @return 修改密码页面
     */
    @GetMapping("/modifyPassPage")
    public String modifyPassPage(ModelMap map) {
        String userName = SecurityUtils.getUserName();
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, userName));
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
        SysUser sysUser = sysUserService.detail(id);
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
        SysUser sysUser = sysUserService.detail(id);
        map.put("sysUser",sysUser);
        return SUFFIX + "/resetPass";
    }

    /**
     * 重置密码
     * @param resetPassDTO 重置用户密码对象
     * @return 重置密码状态
     */
    @CrudLog(title = "重置密码",businessType = BusinessType.UPDATE)
    @PostMapping("/resetPass")
    @ResponseBody
    public Result resetPass(@RequestBody @Validated ResetPassDTO resetPassDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysUserService.resetPass(resetPassDTO);
        return Result.success(flag);
    }

    /**
     * 修改密码
     * @param modifyPassDTO 修改密码对象
     * @return 修改状态
     */
    @CrudLog(title = "修改密码",businessType = BusinessType.UPDATE)
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
     * @param sysUser 用户列表对象
     * @return 请求列表
     */
    @GetMapping("/page")
    @ResponseBody
    public TableDataInfo page(SysUser sysUser) {
        startPage();
        List<SysUser> list = sysUserService.list(new LambdaQueryWrapper<SysUser>()
                .like(StringUtils.isNotBlank(sysUser.getUserName()), SysUser::getUserName , sysUser.getUserName())
                .like(StringUtils.isNotBlank(sysUser.getEmail()), SysUser::getEmail , sysUser.getEmail())
                .like(StringUtils.isNotBlank(sysUser.getPhonenumber()), SysUser::getPhonenumber , sysUser.getPhonenumber()));
        return getDataTable(list);
    }

    /**
     * 添加请求
     * @param sysUser 添加用户对象
     * @return 添加状态
     */
    @CrudLog(title = "添加用户",businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        sysUserService.add(sysUser);
        return Result.success();
    }

    /**
     * 编辑请求
     * @param sysUser 编辑用户对象
     * @return 编辑状态
     */
    @CrudLog(title = "更新用户",businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        sysUserService.edit(sysUser);
        return Result.success();
    }

    /**
     * 编辑用户信息请求
     * @param sysUser 编辑用户对象
     * @return 编辑状态
     */
    @CrudLog(title = "更新个人信息",businessType = BusinessType.UPDATE)
    @PostMapping("/editUserInfo")
    @ResponseBody
    public Result editUserInfo(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysUserService.updateById(sysUser);
        return Result.success(flag);
    }

    /**
     * 修改用户状态
     * @param sysUser 修改用户状态对象
     * @return 修改状态
     */
    @CrudLog(title = "更新用户状态",businessType = BusinessType.UPDATE)
    @PostMapping("/changeUser")
    @ResponseBody
    public Result changeUser(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean flag = sysUserService.updateById(sysUser);
        return Result.success(flag);
    }

    /**
     * 删除请求
     * @param id 用户id
     * @return 删除状态
     */
    @CrudLog(title = "删除用户",businessType = BusinessType.DELETE)
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Result del(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
        sysUserService.del(id);
        return Result.success();
    }

    /**
     * 导出用户信息
     */
    @RequestMapping("/exportExcel")
    public void export(HttpServletRequest request, HttpServletResponse response) {
       try {
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
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
}
