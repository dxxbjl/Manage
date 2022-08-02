package io.github.yangyouwang.crud.system.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
     * 主键id
     */
    @ExcelIgnore
    private Long id;
    /**
     * 账号
     */
    @ExcelProperty(value = {"账号"}, index = 0)
    private String userName;
    /**
     * 启用
     */
    @ExcelIgnore
    private String enabled;
    /**
     * 邮箱
     */
    @ExcelProperty(value = {"邮箱"}, index = 1)
    private String email;
    /**
     * 手机号码
     */
    @ExcelProperty(value = {"手机号码"}, index = 2)
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty(value = {"用户性别"}, index = 3, converter = SexConverter.class)
    private String sex;
    /**
     * 头像
     */
    @ExcelIgnore
    private String avatar;
    /**
     * 备注
     * */
    @ExcelProperty(value = {"备注"}, index = 4)
    private String remark;
}
