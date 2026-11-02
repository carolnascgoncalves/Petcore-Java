package br.com.fiap.petcore.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenha {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senha = encoder.encode("12345678");

        System.out.println(senha);
    }
}