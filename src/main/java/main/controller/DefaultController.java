package main.controller;

import main.api.response.InitResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DefaultController {

    @RequestMapping(value="/")
    public String index() {
//        System.out.println(initResponse.getTitle());
        return "index";
    }
    
}
