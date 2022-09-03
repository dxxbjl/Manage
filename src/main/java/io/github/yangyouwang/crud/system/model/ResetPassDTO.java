package io.github.yangyouwang.crud.system.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Description: 重置密码DTO <br/>
 * date: 2022/8/24 21:25<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class ResetPassDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
