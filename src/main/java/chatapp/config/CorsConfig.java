package chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173",
                                "https://chatapp-u2kw.onrender.com",
                                "https://basic-chat-with-websocket-frontend.vercel.app")
                .allowedMethods("GET",
                                "POST",
                                "PUT",
                                "PATCH",
                                "DELETE",
                                "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
