package io.github.yangyouwang.crud.act.model.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: ActReModelAddDTO
 * @projectName crud
 * @description: 模型添加请求
 * @date 2021/4/10下午2:10
 */
@Data
public class ActReModelAddDTO implements Serializable {

    private static final long serialVersionUID = -402449465184936542L;
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

    private String description;
}
