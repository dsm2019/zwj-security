package io.renren.modules.dnf.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    String upload(MultipartFile file, String filename);

}