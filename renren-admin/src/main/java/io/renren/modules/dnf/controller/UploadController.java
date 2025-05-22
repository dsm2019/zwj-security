package io.renren.modules.dnf.controller;

import io.renren.common.annotation.LogOperation;
import io.renren.common.utils.Result;
import io.renren.modules.upload.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/dnf/upload")
@Tag(name = "DNF 图片上传")
@AllArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    @Operation(summary = "上传文件")
    @LogOperation("上传文件")
//    @RequiresPermissions("dnf:character:save")
    public Result<?> upload(@RequestParam("file") MultipartFile file,
                         @RequestParam(required = false) String filename) {
        String upload = uploadService.upload(file, filename);
        return new Result<>().ok(Map.of("fileUrl", upload));
    }
}
