package io.github.yangyouwang.common.domain;

import lombok.Data;

import java.util.List;

/**
 * Description: 分页数据响应<br/>
 * date: 2022/8/1 18:51<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class TableDataInfo {

    /** 总记录数 */
    private long count;

    /** 列表数据 */
    private List<?> data;

    /** 消息状态码 */
    private int code;
}
