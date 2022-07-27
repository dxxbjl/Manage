package io.github.yangyouwang.crud.system.controller;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.core.aliyun.SampleOSS;
import io.github.yangyouwang.core.properties.MinioProperties;
import io.github.yangyouwang.core.util.MinIoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用请求处理
 *
 * @author crud
 */
@Slf4j
@Controller
@RequestMapping("/sysCommon")
public class SysCommonController {

    private final SampleOSS sampleOSS;

    private final MinIoUtil minIoUtil;

    private final MinioProperties minioProperties;

    @Autowired
    public SysCommonController(SampleOSS sampleOSS,MinIoUtil minIoUtil,MinioProperties minioProperties) {
        this.sampleOSS = sampleOSS;
        this.minIoUtil = minIoUtil;
        this.minioProperties = minioProperties;
    }

    /**
     * 通用上传请求
     * @param fileDir 图片路径
     * @return 图片路径相应
     */
    @PostMapping("/upload")
    @ResponseBody
    @CrudLog
    public Result uploadFile(MultipartFile file,
                             @RequestParam(value = "fileDir",required = false,defaultValue = "img/def") String fileDir) {
        // 上传文件路径
        String url = sampleOSS.upload( file, fileDir);
        Map<String,Object> ajax = new HashMap<>(16);
        ajax.put("fileName", file.getOriginalFilename());
        ajax.put("url", url);
        return Result.success(ajax);
    }

    /**
     * MinIo上传请求
     */
    @PostMapping("/uploadMinIo")
    @ResponseBody
    @CrudLog
    public Result uploadMinIoFile(MultipartFile file) throws Exception {
        if(StringUtils.isEmpty(file.getName())){
            return Result.failure("上传文件名称为空",file.getOriginalFilename());
        }
        log.info("正在做上传操作，上传文件为：{}",file.getOriginalFilename());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if(!(ConfigConsts.IMG_TYPE_DMG.equals(suffix.toUpperCase()) ||
                ConfigConsts.IMG_TYPE_GIF.equals(suffix.toUpperCase()) ||
                ConfigConsts.IMG_TYPE_JPEG.equals(suffix.toUpperCase()) ||
                ConfigConsts.IMG_TYPE_JPG.equals(suffix.toUpperCase()) ||
                ConfigConsts.IMG_TYPE_PNG.equals(suffix.toUpperCase()) ||
                ConfigConsts.IMG_TYPE_SVG.equals(suffix.toUpperCase()))){
            return Result.failure("上传文件不符合规范",file.getOriginalFilename());
        }
        String bucketName = minioProperties.getBucketName();
        String fileName = minIoUtil.minioUpload(file, file.getOriginalFilename(), bucketName);
        String url = minIoUtil.getShowUtrl(fileName, bucketName);
        Map<String,Object> ajax = new HashMap<>(16);
        ajax.put("fileName", fileName);
        ajax.put("url", url);
        return Result.success(ajax);
    }
}