package com.sg.sgapiclientsdk;

import com.sg.sgapiclientsdk.client.SgApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于配置Gigot API客户端的相关参数
 * @author WSG
 */
@Configuration
@ConfigurationProperties("sg-api.client")
@Data
@ComponentScan
public class SgApiClientConfig {

    /**
     * secretId
     */
    private String secretId;

    /**
     * secretId
     */
    private String secretKey;

    /**
     * 返回GigotApiClient的Bean
     *
     * @return GigotApiClient对象
     */
    @Bean
    public SgApiClient sgApiClient(){
        return new SgApiClient(secretId, secretKey);
    }
}
