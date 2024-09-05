package com.yuunik.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.vodservice.service.VodService;
import com.yuunik.vodservice.utils.AliyunVodUtil;
import com.yuunik.vodservice.utils.ConstantVodUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

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

    // 批量删除阿里云 Vod 视频
    @Override
    public void batchDeleteVodVideo(List<String> videoIdList) {
        try {
            // 初始化客户端
            DefaultAcsClient client = AliyunVodUtil.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            // 创建删除视频的请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 拼接视频id
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            // 设置视频id
            request.setVideoIds(videoIds);
            // 调用删除视频的方法
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new YuunikException(20001, "批量删除视频失败");
        }
    }

    // 根据视频阿里云oss点播id获取视频播放凭证
    @Override
    public String getVideoAuthCode(String videoSourceId) {
        try {

            // 获取阿里云存储相关常量
            String accessKeyId = ConstantVodUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantVodUtil.ACCESS_KEY_SECRET;
            // 初始化
            DefaultAcsClient client = AliyunVodUtil.initVodClient(accessKeyId, accessKeySecret);
            // 创建获取视频凭证的请求对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 设置视频id
            request.setVideoId(videoSourceId);
            // 获取响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            // 获取播放凭证
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (Exception e) {
            // 输出异常信息
            e.printStackTrace();
            throw new YuunikException(20001, "获取视频播放凭证失败");
        }
    }
}
