package io.github.yangyouwang.common.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: EnabledDTO
 * @projectName crud
 * @description: 修改状态
 * @date 2021/4/16下午6:09
 */
@Data
public class EnabledDTO implements Serializable {

    private static final long serialVersionUID = -1539484186991323275L;
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 是否启用
     */
    @NotBlank(message = "是否启用不能为空")
    private String enabled;
}
