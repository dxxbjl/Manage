package io.github.yangyouwang.crud.act.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: ActReModelAddReq
 * @projectName crud
 * @description: 模型编辑请求
 * @date 2021/4/10下午2:10
 */
@Data
public class ActReModelEditReq implements Serializable {
    /** ID_ */
    @NotBlank(message = "ID_不能为空")
    private String id;

    /** 乐观锁 */
    private Long rev;

    /** 名称 */
    private String name;

    /** KEY_ */
    private String key;

    /** 分类	 */
    private String category;

    /** 版本 */
    private Long version;

    /** META_INFO_ */
    private String metaInfo;

    /** 部署ID */
    private String deploymentId;

    private String editorSourceValueId;

    private String editorSourceExtraValueId;

    private String tenantId;
}
