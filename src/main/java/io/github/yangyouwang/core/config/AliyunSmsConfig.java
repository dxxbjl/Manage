package io.github.yangyouwang.core.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import io.github.yangyouwang.core.properties.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author modificial
 * @since 2021/4/11
 */
@Configuration
public class AliyunSmsConfig {
    
    private final MailProperties mailProperties;
    public AliyunSmsConfig(MailProperties mailProperties) {
        this.mailProperties=mailProperties;
    }
    
    @Bean
    public IAcsClient iAcsClient(){
        IClientProfile profile = DefaultProfile.getProfile(mailProperties.getRegionId(),
                mailProperties.getAccessKeyId(), mailProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }
}