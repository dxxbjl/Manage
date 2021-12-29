package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysDictTypeListDTO
 * @projectName crud
 * @description: 字典列表请求
 * @date 2021/4/13下午1:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictTypeListDTO extends PageDto implements Serializable {
    private static final long serialVersionUID = 278471990568899626L;

    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典key
     */
    private String dictKey;
}
