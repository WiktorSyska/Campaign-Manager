package com.futurum.campaign_manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global web configuration class for the application.
 * <p>
 * Configures CORS (Cross-Origin Resource Sharing) settings to allow requests
 * from any origin during development/testing. In production, this should be
 * restricted to specific domains.
 * </p>
 *
 * <p>
 * Current configuration:
 * <ul>
 *   <li>Allows all origins (for testing purposes)</li>
 *   <li>Permits GET, POST, PUT, and DELETE methods</li>
 *   <li>Allows all headers</li>
 *   <li>Applies to all endpoints (/**)</li>
 * </ul>
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Zezwól na wszystkie źródła (dla testów)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
