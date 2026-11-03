package br.com.fiap.petcore.control;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/home")
    public ModelAndView home(Authentication authentication) {
        ModelAndView mv = new ModelAndView("home/index");
        mv.addObject("tutor", authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR")));
        mv.addObject("medico", authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDICO")));

        return mv;
    }
}
