package io.github.yangyouwang.crud.system.controller;

import cn.hutool.json.JSONUtil;
import io.github.yangyouwang.common.domain.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description:
 * @date 2021/4/12 11:00
 */
@Controller
@RequestMapping("/sysDict")
public class SysDictController {

    private static final String SUFFIX = "/system/sysDict";

    /**
     * 跳转列表
     * @return 列表页面
     */
    @GetMapping("/listPage")
    public String listPage(){
        return SUFFIX + "/list";
    }

    /**
     * 跳转添加
     * @return 添加页面
     */
    @GetMapping("/addPage")
    public String addPage(){
        return SUFFIX + "/add";
    }


    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/type/list")
    @ResponseBody
    public Result typeList() {
        String data = "{\n" +
                "  \"content\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"dictName\": \"类型名称1\",\n" +
                "      \"dictKey\": \"类型Key1\",\n" +
                "      \"remark\": \"类型1\",\n" +
                "      \"menuIds\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"dictName\": \"类型名称2\",\n" +
                "      \"dictKey\": \"类型Key2\",\n" +
                "      \"remark\": \"类型2\",\n" +
                "      \"menuIds\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageable\": {\n" +
                "    \"sort\": {\n" +
                "      \"sorted\": false,\n" +
                "      \"unsorted\": true,\n" +
                "      \"empty\": true\n" +
                "    },\n" +
                "    \"offset\": 0,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"pageNumber\": 0,\n" +
                "    \"paged\": true,\n" +
                "    \"unpaged\": false\n" +
                "  },\n" +
                "  \"totalPages\": 1,\n" +
                "  \"last\": true,\n" +
                "  \"totalElements\": 2,\n" +
                "  \"number\": 0,\n" +
                "  \"size\": 10,\n" +
                "  \"sort\": {\n" +
                "    \"sorted\": false,\n" +
                "    \"unsorted\": true,\n" +
                "    \"empty\": true\n" +
                "  },\n" +
                "  \"numberOfElements\": 2,\n" +
                "  \"first\": true,\n" +
                "  \"empty\": false\n" +
                "}";
        return Result.success(JSONUtil.parseObj(data));
    }


    /**
     * 列表请求
     * @return 请求列表
     */
    @GetMapping("/value/list")
    @ResponseBody
    public Result valueList() {
        String data = "{\n" +
                "  \"content\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"typeId\": \"1\",\n" +
                "      \"dictionaryValueName\": \"类型值名称1\",\n" +
                "      \"dictionaryValueKey\": \"类型值Key1\",\n" +
                "      \"remark\": \"类型1\",\n" +
                "      \"menuIds\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"typeId\": \"1\",\n" +
                "      \"dictionaryValueName\": \"类型值名称2\",\n" +
                "      \"dictionaryValueKey\": \"类型值Key2\",\n" +
                "      \"remark\": \"类型2\",\n" +
                "      \"menuIds\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageable\": {\n" +
                "    \"sort\": {\n" +
                "      \"sorted\": false,\n" +
                "      \"unsorted\": true,\n" +
                "      \"empty\": true\n" +
                "    },\n" +
                "    \"offset\": 0,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"pageNumber\": 0,\n" +
                "    \"paged\": true,\n" +
                "    \"unpaged\": false\n" +
                "  },\n" +
                "  \"totalPages\": 1,\n" +
                "  \"last\": true,\n" +
                "  \"totalElements\": 2,\n" +
                "  \"number\": 0,\n" +
                "  \"size\": 10,\n" +
                "  \"sort\": {\n" +
                "    \"sorted\": false,\n" +
                "    \"unsorted\": true,\n" +
                "    \"empty\": true\n" +
                "  },\n" +
                "  \"numberOfElements\": 2,\n" +
                "  \"first\": true,\n" +
                "  \"empty\": false\n" +
                "}";
        return Result.success(JSONUtil.parseObj(data));
    }

}
