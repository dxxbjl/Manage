package io.github.yangyouwang;

import io.github.yangyouwang.core.config.ServerConfig;
import io.github.yangyouwang.core.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * CRUD系统启动类
 * @author yangyouwang
 */
@Slf4j
@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
		printAccessUrl();
	}

	/**
	 * 打印访问地址
	 */
	private static void printAccessUrl() {
		ServerConfig serverConfig = SpringUtils.getBean(ServerConfig.class);
		log.info("\n----------------------------------------------------------\n\t" +
				"Application is running! Access URLs:\n\t" +
				"访问网址:"+ serverConfig.getUrl()+ "\n" +
				"----------------------------------------------------------");
	}
}