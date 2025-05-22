package io.renren.modules.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class UploadService {

    public String upload(MultipartFile file, String filename) {
        String path = System.getProperty("user.dir");
        path = path + "/renren-ui/piblic/";

        String fileName = getFileName(filename, Objects.requireNonNull(file.getOriginalFilename()));

        try {
            file.transferTo(new java.io.File(path + fileName));
        } catch (Exception e) {
            log.error("上传失败", e);
            return null;
        }

        log.info("上传成功，文件路径：{}", path + fileName);
        return fileName;
    }


    private static String getFileName(String filename, String originalFilename) {
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "/upload/" + uuid + "-" + filename + suffix;
    }

}
