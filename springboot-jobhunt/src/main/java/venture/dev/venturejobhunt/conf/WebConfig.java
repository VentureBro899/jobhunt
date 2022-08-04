package venture.dev.venturejobhunt.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 图片保存路径，自动从yml文件中获取数据
     *   示例： E:/images/
     */
    @Value("${venture.mysetting.upload-file-path}")
    private String fileSavePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 因为每次重启服务器之后，都会在系统临时文件夹内，
         * 创建一个新的服务器，图片就保存在这里，若重启，又会产生一个新的服务器，
         * 此时访问的就是新服务器的图片资源，而图片根本就不在新服务器内,故不要让
         * 图片保存在临时文件夹中
         * 配置资源映射
         * 意思是：如果访问的资源路径是以“/uploadimages/”开头的，
         * 就给我映射到本机的“E:/images/”这个文件夹内，去找要的资源
         * 注意：${venture.mysetting.file-upload-path}后面的 “/”一定要带上
         */
        registry.addResourceHandler("/uploadimages/**")
                .addResourceLocations("file:"+fileSavePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowCredentials(true);
    }
}


