package io.renren.modules.dnf.service.impl;

import io.renren.modules.dnf.service.UploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UploadServiceImpl implements UploadService {


    @Override
    public String upload(MultipartFile file, String filename) {
        String prefix = System.getProperty("user.dir");
        String path =  "/file/picture";

        path = getFileName(path, filename, Objects.requireNonNull(file.getOriginalFilename()));

        try {
            file.transferTo(new java.io.File(prefix + path));
        } catch (Exception e) {
            log.error("上传失败", e);
            return null;
        }

        log.info("上传成功，文件路径：{}", path);
        return path;
    }


    private static String getFileName(String path, String filename, String originalFilename) {
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

//        if (StringUtils.isNoneBlank(filename)) {
//            path = path + "/" + filename ;
//            if (!new File(prefix + path + suffix).exists()) {
//                return path + suffix;
//            } else {
//                for (int i = 1; i < 100; i++) {
//                    if (!new File(path + "(" + i + ")" + suffix).exists()) {
//                        return path + "(" + i + ")" + suffix;
//                    }
//                }
//            }
//        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        return path + "/" + uuid + "-" + filename + suffix;
    }

}