package io.github.yangyouwang.crud.api;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.github.yangyouwang.common.annotation.ApiVersion;
import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.common.annotation.PassToken;
import io.github.yangyouwang.common.base.CrudController;
import io.github.yangyouwang.common.constant.ApiVersionConstant;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.core.properties.CodeGeneratorProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 生成代码接口控制层 <br/>
 * date: 2022/6/28 10:30<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/{version}/code_generator")
@Api(tags = "CodeGeneratorController", description = "生成代码接口控制层")
public class CodeGeneratorController {

    @Autowired
    private CodeGeneratorProperties codeGeneratorProperties;

    @ApiOperation(value="生成代码接口")
    @ApiVersion(value = ApiVersionConstant.API_V1,group = ApiVersionConstant.SWAGGER_API_V1)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "moduleName", value = "模块名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "tables", value = "表名", required = true, dataType = "String")
    })
    @GetMapping("")
    @PassToken
    @CrudLog
    public Result codeGenerator(String moduleName,String ... tables) {
        createCode(moduleName,tables);
        // TODO: 2022/7/22 导入sql到菜单中
        return Result.success("生成代码在项目工程中");
    }

    /**
     * 代码生成
     * @param moduleName 模块名
     * @param tables 表名
     */
    private void createCode(String moduleName, String... tables) {
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
        //定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        //TODO 是否覆盖已有的文件
        gc.setFileOverride(true);
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
        pc.setModuleName(moduleName);
        pc.setParent("io.github.yangyouwang.crud");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
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
        templateConfig.setService("templates/ftl/java/service.java");
        templateConfig.setServiceImpl("templates/ftl/java/serviceImpl.java");

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
    }
}
