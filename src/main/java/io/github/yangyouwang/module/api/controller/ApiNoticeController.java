package io.github.yangyouwang.module.api.controller;

import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.constant.ApiVersionConsts;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.module.api.service.ApiNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 公告接口<br/>
 * date: 2022/12/6 11:14<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/{version}/notice")
@Api(tags = "公告接口")
@RequiredArgsConstructor
@ResponseResultBody
public class ApiNoticeController {

    private final ApiNoticeService apiNoticeService;

    /**
     * 通知公告接口
     * @return 响应
     */
    @ApiVersion(value = ApiVersionConsts.API_V1,group = ApiVersionConsts.SWAGGER_API_V1)
    @GetMapping("/get_notice")
    @ApiOperation(value="通知公告", notes="通知公告")
    @PassToken
    public Result getNotice() {
        return Result.success(apiNoticeService.getNotice());
    }
}
