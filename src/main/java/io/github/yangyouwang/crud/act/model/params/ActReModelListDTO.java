package io.github.yangyouwang.crud.act.model.params;

import io.github.yangyouwang.common.domain.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: ActReModelListDTO
 * @projectName crud
 * @description: 模型列表请求
 * @date 2021/4/10下午2:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActReModelListDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 2888819015484058143L;
    /** 名称 */
    private String name;

    /** KEY **/
    private String key;
}
