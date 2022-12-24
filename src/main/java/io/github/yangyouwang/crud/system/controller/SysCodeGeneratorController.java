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
import io.github.yangyouwang.core.config.properties.CodeGeneratorProperties;
import io.github.yangyouwang.crud.system.model.vo.CodeConfigVO;
import io.github.yangyouwang.crud.system.model.dto.CodeBuildDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileSystemUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    private static final String SUFFIX = "system/sysCodeGenerator";

    private final CodeGeneratorProperties codeGeneratorProperties;

    /**
     * 跳转代码生成页面
     * @return 代码生成页面
     */
    @GetMapping("/index")
    public String indexPage(ModelMap map) {
        CodeConfigVO codeConfigVO = new CodeConfigVO();
        BeanUtils.copyProperties(codeGeneratorProperties,codeConfigVO);
        map.put("codeConfig",codeConfigVO);
        return SUFFIX + "/index";
    }

    /**
     * 查询表
     * @return 表
     */
    @GetMapping("/tableSelect")
    @ResponseBody
    public List<String> tableSelect() {
        List<String> tableNames = new ArrayList<>();
        try {
            Class.forName(codeGeneratorProperties.getDriverName());
            Connection conn = DriverManager.getConnection(codeGeneratorProperties.getUrl(), codeGeneratorProperties.getUsername(), codeGeneratorProperties.getPassword());
            // 获取数据库元数据
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            System.out.println("conn.getCatalog() = " + conn.getCatalog());
            ResultSet tableRet = databaseMetaData.getTables(conn.getCatalog(), "%", "%", new String[] { "TABLE" });
            while (tableRet.next()) {
                String tableName = (String) tableRet.getObject("TABLE_NAME");
                System.out.println("tableName:" + tableName);
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            throw new RuntimeException("数据库链接错误");
        }
        return tableNames;
    }
    /**
     * 查询字段列表
     * @return 字段列表
     */
    @GetMapping("/fieldList")
    @ResponseBody
    public List fieldList(String table) {
        List result = new ArrayList();
        try {
            Class.forName(codeGeneratorProperties.getDriverName());
            Connection conn = DriverManager.getConnection(codeGeneratorProperties.getUrl(), codeGeneratorProperties.getUsername(), codeGeneratorProperties.getPassword());
            // 获取数据库元数据
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, "%", table, new String[] { "TABLE" });
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if(tableName.equals(table)){
                    ResultSet rs = conn.getMetaData().getColumns(null, conn.getMetaData().getUserName(),tableName.toUpperCase(), "%");
                    while(rs.next()){
                        Map map = new HashMap();
                        String columnName = rs.getString("COLUMN_NAME");
                        map.put("columnName", columnName);
                        String remarks = rs.getString("REMARKS");
                        if(Strings.isBlank(remarks)){
                            remarks = columnName;
                        }
                        map.put("remarks",remarks);
                        String typeName = rs.getString("TYPE_NAME");
                        map.put("typeName",typeName);
                        String columnSize = rs.getString("COLUMN_SIZE");
                        map.put("columnSize",columnSize);
                        result.add(map);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("数据库链接错误");
        }
        return result;
    }
    /**
     * 代码生成接口
     * @param codeBuildDTO 代码生成DTO
     * @return 结果
     */
    @PostMapping("/build")
    @ResponseBody
    public Result build(@RequestBody @Validated CodeBuildDTO codeBuildDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String path = System.getProperty("user.dir");
        this.codeBuild(codeBuildDTO,path);
        return Result.success("生成代码在项目工程中");
    }

    /**
     * 代码生成并下载ZIP压缩包
     * @param codeBuildDTO 代码生成DTO
     */
    @GetMapping("/buildZip")
    public void buildZip(CodeBuildDTO codeBuildDTO,  HttpServletResponse response)
            throws Exception {
        String path = System.getProperty("user.dir") + "/temp";
        this.codeBuild(codeBuildDTO, path);
        response.setHeader("Content-disposition", "attachment; filename=code.zip");
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        ZipDirectory(path, response.getOutputStream());
        // 递归删除目录
        FileSystemUtils.deleteRecursively(new File(path));
    }

    /**
     * 生成代码逻辑
     * @param codeBuildDTO 生成代码DTO
     */
    public void codeBuild(CodeBuildDTO codeBuildDTO,String path) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(path + "/src/main/java");
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
        pc.setModuleName(codeBuildDTO.getModuleName());
        pc.setParent("io.github.yangyouwang.crud");
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>(16);
                map.put("menuId", codeBuildDTO.getMenuId());
                this.setMap(map);
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/ftl/java/service.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/io/github/yangyouwang/crud/" + pc.getModuleName()
                        + "/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return path + "/src/main/resources/mapper/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("templates/ftl/html/list.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return path + "/src/main/resources/templates/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityPath() + StringPool.SLASH + "list.html";
            }
        });
        focList.add(new FileOutConfig("templates/ftl/html/add.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return path + "/src/main/resources/templates/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityPath() + StringPool.SLASH + "add.html";
            }
        });
        focList.add(new FileOutConfig("templates/ftl/html/edit.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return path + "/src/main/resources/templates/" + pc.getModuleName()
                        + StringPool.SLASH + tableInfo.getEntityPath() + StringPool.SLASH + "edit.html";
            }
        });
        focList.add(new FileOutConfig("templates/ftl/sql/menu.sql.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return path + "/src/main/resources/sql/" + pc.getModuleName()
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
        String[] tables = new String[]{codeBuildDTO.getTables()};
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

    /**
     * 一次性压缩多个文件，文件存放至一个文件夹中
     */
    public static void ZipDirectory(String directoryPath, ServletOutputStream outputStream) {
        try {
            ZipOutputStream output = new ZipOutputStream(outputStream);
            List<File> files = getFiles(new File(directoryPath));
            for (File file : files) {
                try (InputStream input = new FileInputStream(file)) {
                    output.putNextEntry(new ZipEntry(file.getPath().substring(directoryPath.length() + 1)));
                    int temp;
                    while ((temp = input.read()) != -1) {
                        output.write(temp);
                    }
                }
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<File> getFiles(File file) {
        List<File> files = new ArrayList<>();
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            if (subFile.isDirectory()) {
                List<File> subFiles = getFiles(subFile);
                files.addAll(subFiles);
            } else {
                files.add(subFile);
            }
        }
        return files;
    }
}
