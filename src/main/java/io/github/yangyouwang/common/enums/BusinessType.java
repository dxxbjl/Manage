package io.github.yangyouwang.common.enums;

/**
 * Description: LOG业务类型枚举 <br/>
 * date: 2022/8/28 22:58<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
public enum BusinessType {
    /**
     * 新增数据
     *  */
    INSERT("新增数据"),
    /**
     * 更新数据
     *  */
    UPDATE("更新数据"),
    /**
     * 删除数据
     *  */
    DELETE("删除数据"),
    /**
     * 查询数据
     *  */
    SELECT("查询数据");
    /**
     * 业务类型
     *  */
    public String type;

    BusinessType(String type) {
        this.type = type;
    }
}
