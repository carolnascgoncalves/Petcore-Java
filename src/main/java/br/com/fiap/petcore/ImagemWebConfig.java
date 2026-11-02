package br.com.fiap.petcore;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagemWebConfig implements WebMvcConfigurer {

    @Value("${petcore.upload-dir:uploads/pets}")
    private String diretorio;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path pasta = Path.of(diretorio).toAbsolutePath().normalize();

        String local = pasta.toUri().toString();

        if (!local.endsWith("/")) {
            local += "/";
        }

        registry.addResourceHandler("/uploads/pets/**")
                .addResourceLocations(local);
    }
}