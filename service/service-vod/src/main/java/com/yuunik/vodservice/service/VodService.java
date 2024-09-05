package com.yuunik.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile videoFile);

    void deleteVodVideo(String videoId);

    void batchDeleteVodVideo(List<String> videoIdList);

    String getVideoAuthCode(String videoSourceId) throws Exception;
}
