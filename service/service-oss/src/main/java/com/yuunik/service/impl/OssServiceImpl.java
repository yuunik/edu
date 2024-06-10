package com.yuunik.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.service.OssService;
import com.yuunik.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    // 上传文件
    @Override
    public String uploadFile(MultipartFile file) {
        // 获取 oss 相关配置
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建 oss 客户端
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            // 文件名格式化
            String uuidName = UUID.randomUUID().toString().replaceAll("-", "");
            String currentDate = new DateTime().toString("yyyy/MM/dd");
            String fileName = currentDate + "/" + uuidName + file.getOriginalFilename();

            // 上传文件
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭 oss 客户端
            ossClient.shutdown();

            // 拼接上传文件
            String url = "https://"+ bucketName +"."+ endpoint +"/" + fileName;

            return url;
        } catch (Exception e) {
            // 输出异常
            e.printStackTrace();
            // 抛出异常
            throw new YuunikException(20001, "上传文件失败");
        }
    }
}
