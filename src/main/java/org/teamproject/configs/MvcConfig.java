package org.teamproject.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${file.upload.url}")
    private String fileUploadUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 파일 업로드 경로 설정 */
        registry.addResourceHandler(fileUploadUrl + "**")
                .addResourceLocations("file:///" + fileUploadPath);
    }
}
