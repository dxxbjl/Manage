package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysMenuVisibleReq
 * @projectName crud
 * @description: 修改菜单状态
 * @date 2021/4/16下午8:15
 */
@Data
public class SysMenuVisibleReq implements Serializable {
    private static final long serialVersionUID = 5932349475956234610L;
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 菜单状态（Y显示 N隐藏）
     */
    @NotBlank(message = "菜单状态不能为空")
    private String visible;
}
