package io.github.yangyouwang.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.system.model.req.SysLogListReq;
import io.github.yangyouwang.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yangyouwang
 * @title: SysLogController
 * @projectName crud
 * @description: 日志控制层
 * @date 2021/4/111:06 AM
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController {


    private static final String SUFFIX = "/system/sysLog";

    @Autowired
    private SysLogService sysLogService;

    /**
     * 跳转列表
     * @return 列表页面
     */
    @GetMapping("/listPage")
    public String listPage(){
        return SUFFIX + "/list";
    }

    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result list(SysLogListReq sysLogListReq){
        return Result.success(sysLogService.list(sysLogListReq));
    }

}
