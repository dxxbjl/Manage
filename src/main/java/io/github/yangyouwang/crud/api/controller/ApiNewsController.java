package io.github.yangyouwang.crud.api.controller;

import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.annotation.ResponseResultBody;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.TableDataInfo;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.app.entity.News;
import io.github.yangyouwang.crud.app.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description: 新闻接口<br/>
 * date: 2022/11/1 20:30<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/{version}/news")
@Api(tags = "新闻接口")
@RequiredArgsConstructor
@ResponseResultBody
public class ApiNewsController extends CrudController {

    private final NewsService newsService;

    @ApiOperation(value = "新闻表分页列表", response = News.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", dataType = "Integer")})
    @GetMapping(value = "/page")
    @ResponseBody
    @PassToken
    public TableDataInfo page(News param) {
        startPage();
        List<News> data = newsService.page(param);
        return getDataTable(data);
    }

    /**
     * 新闻详情
     * @param id 主键
     * @return 响应
     */
    @ApiOperation(value = "新闻详情")
    @GetMapping("/info/{id}")
    @PassToken
    public News info(@Valid @NotNull(message = "id不能为空") @PathVariable Long id) {
        News info = newsService.info(id);
        info.setContent(StringUtil.toHtml(info.getContent()));
        return info;
    }
}
