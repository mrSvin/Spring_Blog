package main.controller;

import main.api.response.InitResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DefaultController {

    @RequestMapping(value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(value="/posts/recent")
    public String recent() {
        return "index";
    }

    @RequestMapping(value="/posts/popular")
    public String popular() {
        return "index";
    }

    @RequestMapping(value="/posts/best")
    public String best() {
        return "index";
    }

    @RequestMapping(value="/posts/early")
    public String early() {
        return "index";
    }
    
}
