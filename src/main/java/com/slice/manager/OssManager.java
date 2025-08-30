package com.slice.manager;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.slice.config.OssConfig;
import com.slice.enums.ErrorCode;
import com.slice.exception.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public String putObject(String key, MultipartFile multipartFile) {
        boolean bucketExist = aliOssClient.doesBucketExist(ossConfig.getBucketName());
        if(!bucketExist) {
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(ossConfig.getBucketName());
            createBucketRequest.setCannedACL(CannedAccessControlList.Private);
            aliOssClient.createBucket(createBucketRequest);
        }
        try(InputStream inputStream = multipartFile.getInputStream()) {
            aliOssClient.putObject(ossConfig.getBucketName(), key, inputStream);
            return String.format("https://%s.%s/%s", ossConfig.getBucketName(),ossConfig.getEndpoint(),key);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件上传失败");
        }
    }
}