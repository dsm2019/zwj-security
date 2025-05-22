/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.controller;

import io.renren.service.SysGeneratorService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/generator")
public class SysGeneratorController {
    @Resource
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtil = sysGeneratorService.queryList(new Query(params));

        return R.ok().put("page", pageUtil);
    }

    /**
     * 生成代码
     */
    @GetMapping("/code")
    public R code(@RequestParam String tables) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));

//        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
//        response.addHeader("Content-Length", "" + data.length);
//        response.setContentType("application/octet-stream; charset=UTF-8");
//
//        IoUtil.write(response.getOutputStream(), false, data);

        // 获取当前工作目录
        String path = System.getProperty("user.dir");
        // 拼接代码存储路径
        path = path + "/renren-generator/src/main/java/code";

        // 创建文件对象
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File outputFile = new File(directory, "renren.zip");
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(data);
            System.out.println("文件已保存到: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }

        return R.ok();
    }
}
