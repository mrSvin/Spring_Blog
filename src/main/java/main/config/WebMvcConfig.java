package main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Данный метод помогает Spring обнаруживать пути к файлам
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        String myExternalFilePath = "file:///D:/java/projects/skillbox/DIPLOM/blog/Spring_Blog/src/main/resources/upload/";
        registry.addResourceHandler("/upload/**").addResourceLocations(myExternalFilePath);

    }
}
