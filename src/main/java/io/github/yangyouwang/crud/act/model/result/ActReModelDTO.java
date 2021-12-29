package io.github.yangyouwang.crud.act.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangyouwang
 * @title: ActReModelDTO
 * @projectName crud
 * @description: 模型响应
 * @date 2021/4/10下午2:15
 */
@Data
public class ActReModelDTO implements Serializable {
    private static final long serialVersionUID = 6787178412838338861L;
    /** ID_ */
    private String id;

    /** 乐观锁 */
    private Long rev;

    /** 名称 */
    private String name;

    /** KEY_ */
    private String key;

    /** 分类	 */
    private String category;

    /** 创建时间 */
    private Date createTime;

    /** 最新修改时间 */
    private Date lastUpdateTime;

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
