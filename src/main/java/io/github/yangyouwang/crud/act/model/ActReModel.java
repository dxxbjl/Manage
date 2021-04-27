package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程模型对象 act_re_model
 * 
 * @author yangyouwang
 * @date 2020-07-24
 */
@Data
public class ActReModel implements Serializable {

    private static final long serialVersionUID = 1L;

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
