package io.github.yangyouwang.crud.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description: 首页响应 <br/>
 * date: 2022/8/12 13:02<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@ApiModel("首页VO")
public class IndexVO {
    /**
     * 轮播图列表
     */
    @ApiModelProperty("轮播图列表")
    private List<AdVO> adVOList;
    /**
     * 通知公告列表
     */
    @ApiModelProperty("通知公告列表")
    private String noticeList;
    /**
     * 分类列表
     */
    @ApiModelProperty("分类列表")
    private List<CategoryVO> categoryList;

    @Data
    @ApiModel("轮播图VO")
    public static class AdVO {
        /**
         * 广告标题
         */
        @ApiModelProperty("广告标题")
        private String title;
        /**
         * 广告宣传图片
         */
        @ApiModelProperty("广告宣传图片")
        private String url;
    }
    @Data
    @ApiModel("分类VO")
    public static class CategoryVO {
        /**
         * 主键
         */
        @ApiModelProperty("主键")
        private Long id;
        /**
         * 分类名称
         */
        @ApiModelProperty("分类名称")
        private String title;
        /**
         * 图标
         */
        @ApiModelProperty("图标")
        private String image;
    }
}
