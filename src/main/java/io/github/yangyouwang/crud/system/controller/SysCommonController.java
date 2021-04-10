package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.core.aliyun.SampleOSS;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用请求处理
 * 
 * @author crud
 */
@Controller
@RequestMapping("/sysCommon")
public class SysCommonController {

    /**
     * 通用上传请求
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result uploadFile(MultipartFile file,
                             @RequestParam(value = "fileDir",required = false,defaultValue = "img/def") String fileDir) {
        // 上传文件路径
        String url = SampleOSS.upload( file, fileDir);
        Map<String,Object> ajax = new HashMap<>();
        ajax.put("fileName", file.getOriginalFilename());
        ajax.put("url", url);
        return Result.success(ajax);
    }
}
