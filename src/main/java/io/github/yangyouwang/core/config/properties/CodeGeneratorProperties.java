package io.github.yangyouwang.core.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Description: 配置代码生成 <br/>
 * date: 2022/6/28 11:19<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
@Component
@ConfigurationProperties(prefix = CodeGeneratorProperties.PREFIX)
public class CodeGeneratorProperties {

    public static final String PREFIX = "code-generator";
    /**
     * 数据库连接地址
     */
    private String url;
    /**
     * 数据库驱动
     */
    private String driverName;
    /**
     * 数据库账号
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 作者
     */
    private String author;
}
