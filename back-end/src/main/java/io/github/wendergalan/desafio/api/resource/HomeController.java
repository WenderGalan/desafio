package io.github.wendergalan.desafio.api.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * The type Home controller.
 */
@RestController
public class HomeController {

    /**
     * Home model and view.
     *
     * @return the model and view
     */
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:" + "swagger-ui.html");
    }
}
