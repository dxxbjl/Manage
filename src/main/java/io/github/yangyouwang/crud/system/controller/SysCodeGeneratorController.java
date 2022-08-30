package io.github.yangyouwang.crud.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.core.properties.CodeGeneratorProperties;
import io.github.yangyouwang.crud.system.model.CodeGeneratorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description: 代码生成接口控制层 <br/>
 * date: 2022/8/30 23:27<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sysCodeGenerator")
@RequiredArgsConstructor
public class SysCodeGeneratorController extends CrudController {

    private static final String SUFFIX = "system/codeGenerator";

    private final CodeGeneratorProperties codeGeneratorProperties;

    /**
     * 跳转代码生成页面
     * @return 代码生成页面
     */
    @GetMapping("/index")
    public String listPage(){
        return SUFFIX + "/index";
    }

    /**
     * 代码生成接口
     * @param codeGeneratorDTO 代码生成DTO
     * @return 结果
     */
    @PostMapping("/create")
    @ResponseBody
    public Result codeGenerator(@RequestBody @Validated CodeGeneratorDTO codeGeneratorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return Result.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(codeGeneratorProperties.getAuthor());
        gc.setOpen(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        //TODO 是否覆盖已有的文件
        gc.setFileOverride(true);
        // 自定义Service模板 文件名
        gc.setServiceName("%sService");
        //定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(codeGeneratorProperties.getUrl());
        dsc.setDriverName(codeGeneratorProperties.getDriverName());
        dsc.setUsername(codeGeneratorProperties.getUsername());
        dsc.setPassword(codeGeneratorProperties.getPassword());
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(codeGeneratorDTO.getModuleName());
        pc.setParent("io.github.yangyouwang.crud");
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/ftl/java/service.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/io/github/yangyouwang/crud/" + pc.getModuleName()
                        + "/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("templates/ftl/html/list.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/templates/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityPath() + StringPool.SLASH + "list.html";
            }
        });
        focList.add(new FileOutConfig("templates/ftl/html/add.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/templates/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityPath() + StringPool.SLASH + "add.html";
            }
        });
        focList.add(new FileOutConfig("templates/ftl/html/edit.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/templates/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityPath() + StringPool.SLASH + "edit.html";
            }
        });
        focList.add(new FileOutConfig("templates/ftl/sql/menu.sql.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/sql/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityPath() + StringPool.SLASH + "menu.sql";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setController("templates/ftl/java/controller.java");
        // 关闭原有生成
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setSuperControllerClass(CrudController.class);
        strategy.setSuperEntityColumns("id","create_by","create_time","update_by","update_time","deleted","remark");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
        String[] tables = new String[]{codeGeneratorDTO.getTables()};
        strategy.setInclude(tables);
        //url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //生成实体时去掉表前缀
        strategy.setTablePrefix(pc.getModuleName() + "_");
        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
        return Result.success("生成代码在项目工程中");
    }
}
