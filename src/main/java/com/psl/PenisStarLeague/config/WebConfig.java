package com.psl.PenisStarLeague.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedOrigins("http://localhost:4200")
                    .allowedHeaders("*") // Allow all headers
                     .allowCredentials(true); // Allow credentials (e.g
            }
        };
    }
    
}
            // registry.addMapping("/**") // Apply to all endpoints
            //         .allowedOrigins("http://your-client-origin.com") // Replace with your client's origin
            //         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            //         .allowedHeaders("*") // Allow all headers
            //         .allowCredentials(true); // Allow credentials (e.g.