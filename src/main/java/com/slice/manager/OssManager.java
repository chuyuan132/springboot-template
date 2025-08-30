package com.slice.manager;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.slice.config.OssConfig;
import com.slice.enums.ErrorCode;
import com.slice.exception.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * 阿里云oss存储服务
 */
@Component
public class OssManager {

    @Resource
    private OssConfig ossConfig;

    @Resource
    private OSS aliOssClient;

    public String putObject(String key, InputStream stream) {
        try {
            aliOssClient.putObject(ossConfig.getBucketName(), key, stream);
            return "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + key;
        } catch (OSSException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } finally {
            aliOssClient.shutdown();
        }
    }
}
