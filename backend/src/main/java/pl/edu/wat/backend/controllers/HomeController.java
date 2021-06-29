package pl.edu.wat.backend.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController implements ErrorController {
    @RequestMapping("/error")
    public String home() {
        return "forward:/index.html";
    }

    @Override
    public String getErrorPath() {
        return "forward:/index.html";
    }
}
