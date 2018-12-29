package lk.project.marketing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2018/4/2.
 */
@ConfigurationProperties(prefix = "lk.storage")
@EnableConfigurationProperties
@Component
public class StorageConfig {

    /**
     * ceph授权Key
     */
    private String accessKey;

    /**
     * ceph授权密钥
     */
    private String secretKey;

    /**
     * ceph终结点地址
     */
    private String endpointAddr;

    /**
     * ceph基本地址(部署项目的地址)
     */
    private String baseUrl;

    /**
     * ceph桶名称
     */
    private String bucketNamePrefix;

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

    public String getEndpointAddr() {
        return endpointAddr;
    }

    public void setEndpointAddr(String endpointAddr) {
        this.endpointAddr = endpointAddr;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBucketNamePrefix() {
        return bucketNamePrefix;
    }

    public void setBucketNamePrefix(String bucketNamePrefix) {
        this.bucketNamePrefix = bucketNamePrefix;
    }
}
