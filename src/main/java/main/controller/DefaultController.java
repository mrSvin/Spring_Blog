package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DefaultController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/posts/*")
    public String posts() {
        return "index";
    }

    @RequestMapping(value = "/settings")
    public String settings() {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "index";
    }

}


