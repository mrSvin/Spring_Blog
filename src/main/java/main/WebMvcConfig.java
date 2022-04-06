package main;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfig  {

    /**
     * Данный метод помогает Spring обнаруживать пути к файлам
     */
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/");

    }
}
