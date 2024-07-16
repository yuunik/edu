package com.yuunik.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.vodservice.service.VodService;
import com.yuunik.vodservice.utils.AliyunVodUtil;
import com.yuunik.vodservice.utils.ConstantVodUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {
    // 上传视频至阿里云VOD
    @Override
    public String uploadVideo(MultipartFile videoFile) {
        // 初始化返回视频id
        String videoId = "";
        try {
            // 获取上传视频的输入流
            InputStream inputStream = videoFile.getInputStream();
            // 获取上传视频的标题
            String fileName = videoFile.getOriginalFilename();
            // 截取文件名的后缀名
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            // 创建上传视频的请求对象
            UploadStreamRequest request =new UploadStreamRequest(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            // 创建上传视频的响应对象
            UploadVideoImpl uploader = new UploadVideoImpl();
            // 调用上传视频的方法
            UploadStreamResponse response = uploader.uploadStream(request);
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
                throw new YuunikException(Integer.parseInt(response.getCode()), response.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new YuunikException(20001, "上传视频失败");
        }
        return videoId;
    }

    // 删除阿里云 Vod 视频
    @Override
    public void deleteVodVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodUtil.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println("RequestId = " + response.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new YuunikException(20001, "删除视频失败");
        }
    }
}
