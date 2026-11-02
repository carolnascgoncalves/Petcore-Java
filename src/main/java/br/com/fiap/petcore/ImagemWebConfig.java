package br.com.fiap.petcore;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagemWebConfig implements WebMvcConfigurer {

    @Value("${petcore.upload-dir:uploads/pets}")
    private String diretorioPets;

    @Value("${petcore.upload-dir-usuarios:uploads/usuarios}")
    private String diretorioUsuarios;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path pastaPets = Path.of(diretorioPets).toAbsolutePath().normalize();
        String localPets = pastaPets.toUri().toString();

        if (!localPets.endsWith("/")) {
            localPets += "/";
        }

        registry.addResourceHandler("/uploads/pets/**")
                .addResourceLocations(localPets);

        Path pastaUsuarios = Path.of(diretorioUsuarios).toAbsolutePath().normalize();
        String localUsuarios = pastaUsuarios.toUri().toString();

        if (!localUsuarios.endsWith("/")) {
            localUsuarios += "/";
        }

        registry.addResourceHandler("/uploads/usuarios/**")
                .addResourceLocations(localUsuarios);
    }
}