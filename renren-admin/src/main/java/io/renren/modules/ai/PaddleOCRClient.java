package io.renren.modules.ai;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PaddleOCRClient {

    public String extractTextFromImage(MultipartFile source) {
        String apiUrl = "http://localhost:5000/ocr"; // PaddleOCR server URL
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        File file = null;
        try {
            file = convertMultipartFileToFile(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 添加文件（使用 Resource 包装文件）
        body.add("image", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        // 对返回的字符串进行 Unicode 解码
        return decodeUnicode(response.getBody());
    }

    // 把MultipartFile转为File
    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convFile;
    }

    // 解码 Unicode 转义序列的方法
    private static String decodeUnicode(String str) {
        Pattern pattern = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher matcher = pattern.matcher(str);
        StringBuilder sb = new StringBuilder(str.length());
        while (matcher.find()) {
            String code = matcher.group(1);
            char ch = (char) Integer.parseInt(code, 16);
            matcher.appendReplacement(sb, Character.toString(ch));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String extractTextFromImage(String imagePath) {
        String apiUrl = "http://localhost:5000/ocr"; // PaddleOCR server URL
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        File file = new File(imagePath);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 添加文件（使用 Resource 包装文件）
        body.add("image", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        // 对返回的字符串进行 Unicode 解码
        return decodeUnicode(response.getBody());
    }

    public static void main(String[] args) {
        String imagePath = "/Users/weizhuang/Pictures/e37ff723a7244c76ba81df15fd530c78.jpeg~tplv-a9rns2rl98-image.jpeg"; // Replace with your image path
        String extractedText = extractTextFromImage(imagePath);
        System.out.println("Extracted Text: " + extractedText);
    }
}