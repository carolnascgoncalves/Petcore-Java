package br.com.fiap.petcore.control;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MenuModelAdvice {

    @ModelAttribute("menuMedico")
    public boolean menuMedico(Authentication authentication) {

        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO"));
    }
}