package io.github.yangyouwang.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 * 
 * @author crud
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@SelectBeforeUpdate
public abstract class BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    /** 创建者 */
    @CreatedBy
    @Column(name = "create_by",updatable = false)
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    @Column(name = "create_time",updatable = false)
    private Date createTime;

    /** 更新者 */
    @LastModifiedBy
    @Column(name = "update_by")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    /** 备注 */
    @Column(name = "remark")
    private String remark;
}