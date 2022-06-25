package io.github.yangyouwang.common.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description: 分页请求参数<br/>
 * date: 2021/12/29 22:34<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class PageDTO {
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
}
