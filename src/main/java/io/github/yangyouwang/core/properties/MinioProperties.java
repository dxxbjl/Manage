package io.github.yangyouwang.core.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * minio 配置文件
 * 
 * @author yangyouwang
 */
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties
{
    /**
     * ip：minio地址，分布式节点情况下推荐配置一个nginx路由，转接给nginx的负载均衡
     */
    private String endpoint;

    /**
     * 账号
     */
    private String accessKey;

    /**
     * 秘钥
     */
    private String secretKey;
    /**
     * bucketName
     */
    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
