package io.github.yangyouwang.crud.act.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 流程模型对象 act_re_model
 * 
 * @author yangyouwang
 * @date 2020-07-24
 */
@Data
@TableName("ACT_RE_MODEL")
@ApiModel(value="ActReModel对象", description="流程模型对象")
public class ActReModel {

    /** ID_ */
    @TableId("ID_")
    private String id;

    /** 乐观锁 */
    @TableField("REV_")
    private Long rev;

    /** 名称 */
    @TableField("NAME_")
    private String name;

    /** KEY_ */
    @TableField("KEY_")
    private String key;

    /** 分类	 */
    @TableField("CATEGORY_")
    private String category;

    /** 创建时间 */
    @TableField("CREATE_TIME_")
    private Date createTime;

    /** 最新修改时间 */
    @TableField("LAST_UPDATE_TIME_")
    private Date lastUpdateTime;

    /** 版本 */
    @TableField("VERSION_")
    private Long version;

    /** META_INFO_ */
    @TableField("META_INFO_")
    private String metaInfo;

    /** 部署ID */
    @TableField("DEPLOYMENT_ID_")
    private String deploymentId;

    @TableField("EDITOR_SOURCE_VALUE_ID_")
    private String editorSourceValueId;

    @TableField("EDITOR_SOURCE_EXTRA_VALUE_ID_")
    private String editorSourceExtraValueId;

    @TableField("TENANT_ID_")
    private String tenantId;
    /**
     * 描述
     */
    @TableField(exist = false)
    private String description;
}
