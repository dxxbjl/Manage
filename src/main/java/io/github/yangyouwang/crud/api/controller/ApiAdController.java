package io.github.yangyouwang.crud.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.constant.ApiVersionConsts;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.api.model.vo.AdVO;
import io.github.yangyouwang.crud.app.entity.Ad;
import io.github.yangyouwang.crud.app.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 轮播图接口<br/>
 * date: 2022/11/5 20:29<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/{version}/ad")
@Api(tags = "轮播图接口")
@RequiredArgsConstructor
@ResponseResultBody
public class ApiAdController {

    private final AdService adService;

    /**
     * 轮播图列表接口
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @GetMapping("/get_ad_list")
    @ApiOperation(value="轮播图列表接口", notes="轮播图列表接口")
    @PassToken
    public List<AdVO> getAdList() {
        // 获取轮播图列表
        List<Ad> adList = adService.list(new LambdaQueryWrapper<Ad>()
                .select(Ad::getId,Ad::getAdUrl)
                .eq(Ad::getEnabled, ConfigConsts.SYS_YES).orderByDesc(Ad::getCreateBy));
        return adList.stream().map(s -> {
            AdVO adVO = new AdVO();
            adVO.setId(s.getId());
            adVO.setUrl(s.getAdUrl());
            return adVO;
        }).collect(Collectors.toList());
    }
    /**
     * 轮播图详情
     * @param id 主键
     * @return 响应
     */
    @ApiOperation(value = "轮播图详情")
    @GetMapping("/info/{id}")
    @PassToken
    public Ad info(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
        Ad info = adService.info(id);
        info.setAdContent(StringUtil.toHtml(info.getAdContent()));
        return info;
    }
}
