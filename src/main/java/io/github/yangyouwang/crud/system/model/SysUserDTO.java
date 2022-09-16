package io.github.yangyouwang.crud.system.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.metadata.BaseRowModel;
import io.github.yangyouwang.core.converter.SexConverter;
import lombok.Data;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserDTO
 * @projectName crud
 * @description: 用户响应
 * @date 2021/3/254:43 PM
 */
@Data
public class SysUserDTO extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = -4440639783323640070L;
    /**
     * 编号
     */
    @ColumnWidth(30)
    @ExcelProperty(value = {"编号"}, index = 0)
    private Long id;
    /**
     * 账号
     */
    @ColumnWidth(30)
    @ExcelProperty(value = {"账号"}, index = 1)
    private String userName;
    /**
     * 邮箱
     */
    @ColumnWidth(30)
    @ExcelProperty(value = {"邮箱"}, index = 2)
    private String email;
    /**
     * 手机号码
     */
    @ColumnWidth(30)
    @ExcelProperty(value = {"手机号码"}, index = 3)
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ColumnWidth(30)
    @ExcelProperty(value = {"用户性别"}, index = 4, converter = SexConverter.class)
    private String sex;
    /**
     * 角色
     */
    @ColumnWidth(50)
    @ExcelProperty(value = {"角色"}, index = 5)
    private String roleName;
    /**
     * 部门
     */
    @ColumnWidth(30)
    @ExcelProperty(value = {"部门"}, index = 6)
    private String deptName;
    /**
     * 岗位
     */
    @ColumnWidth(50)
    @ExcelProperty(value = {"岗位"}, index = 7)
    private String postName;
    /**
     * 备注
     * */
    @ColumnWidth(30)
    @ExcelProperty(value = {"备注"}, index = 8)
    private String remark;
}
