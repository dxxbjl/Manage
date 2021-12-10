package io.github.yangyouwang.crud.act.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: ActReModelListReq
 * @projectName crud
 * @description: 模型列表请求
 * @date 2021/4/10下午2:07
 */
@Data
public class ActReModelListReq implements Serializable {

    /**
     * pageNum
     */
    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;
    /**
     * pageSize
     */
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    /** 名称 */
    private String name;

    /** KEY **/
    private String key;
}
