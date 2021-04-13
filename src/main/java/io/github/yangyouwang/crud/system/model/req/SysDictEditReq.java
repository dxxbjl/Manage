package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysDictAddReq
 * @projectName crud
 * @description: 字典编辑请求
 * @date 2021/4/13下午2:27
 */
@Data
public class SysDictEditReq implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 类型key
     */
    private String dictKey;
    /**
     * 类型名称
     */
    private String dictName;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 启用
     */
    private String enabled;

    /** 备注 */
    private String remark;

    /**
     * 字典类型与字典项 一对多
     */
    private List<SysDictValueDto> sysDictValues;

    @Data
    public static class SysDictValueDto {
        /**
         * 字典值key
         */
        private String dictValueKey;

        /**
         * 字典值名称
         */
        private String dictValueName;
        /**
         * 显示顺序
         */
        private Integer orderNum;
        /** 备注 */
        private String remark;
    }
}
