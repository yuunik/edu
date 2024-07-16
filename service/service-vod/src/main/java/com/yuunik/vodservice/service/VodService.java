package com.yuunik.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVideo(MultipartFile videoFile);

    void deleteVodVideo(String videoId);
}
