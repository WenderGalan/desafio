package io.github.wendergalan.desafio.api.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:" + "swagger-ui.html");
    }
}
