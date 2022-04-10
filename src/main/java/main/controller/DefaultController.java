package main.controller;

import main.api.response.InitResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET},value = "/login/change-password/{path:[^\\\\.]*}")
    public String newPassword() {
        return "index";
    }

}
