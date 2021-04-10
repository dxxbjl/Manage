package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程模型对象 act_re_model
 * 
 * @author yangyouwang
 * @date 2020-07-24
 */
@Data
@Entity
@Table(name="ACT_RE_MODEL")
public class ActReModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID_ */
    @Id
    @Column(name="ID_")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;

    /** 乐观锁 */
    @Column(name="REV_")
    private Long rev;

    /** 名称 */
    @Column(name="NAME_")
    private String name;

    /** KEY_ */
    @Column(name="KEY_")
    private String key;

    /** 分类	 */
    @Column(name="CATEGORY_")
    private String category;

    /** 创建时间 */
    @Column(name="CREATE_TIME_")
    private Date createTime;

    /** 最新修改时间 */
    @Column(name="LAST_UPDATE_TIME_")
    private Date lastUpdateTime;

    /** 版本 */
    @Column(name="VERSION_")
    private Long version;

    /** META_INFO_ */
    @Column(name="META_INFO_")
    private String metaInfo;

    /** 部署ID */
    @Column(name="DEPLOYMENT_ID_")
    private String deploymentId;

    @Column(name="EDITOR_SOURCE_VALUE_ID_")
    private String editorSourceValueId;

    @Column(name="EDITOR_SOURCE_EXTRA_VALUE_ID_")
    private String editorSourceExtraValueId;

    @Column(name="TENANT_ID_")
    private String tenantId;
}
