package br.com.fiap.petcore.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemPetService {

    @Value("${petcore.upload-dir:uploads/pets}")
    private String diretorio;

    public String salvar(MultipartFile arquivo) throws IOException {

        if (arquivo == null || arquivo.isEmpty()) {
            return null;
        }

        if (arquivo.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("A imagem deve ter no máximo 5 MB.");
        }

        String tipo = arquivo.getContentType();

        String extensao;

        if ("image/jpeg".equals(tipo)) {
            extensao = ".jpg";
        } else if ("image/png".equals(tipo)) {
            extensao = ".png";
        } else if ("image/webp".equals(tipo)) {
            extensao = ".webp";
        } else {
            throw new IllegalArgumentException("Formato inválido. Escolha uma imagem JPG, PNG ou WEBP.");
        }

        Path pasta = Path.of(diretorio).toAbsolutePath().normalize();
        Files.createDirectories(pasta);

        String nomeArquivo = UUID.randomUUID() + extensao;
        Path destino = pasta.resolve(nomeArquivo);

        Files.copy(
                arquivo.getInputStream(),
                destino,
                StandardCopyOption.REPLACE_EXISTING
        );

        return "/uploads/pets/" + nomeArquivo;
    }
}