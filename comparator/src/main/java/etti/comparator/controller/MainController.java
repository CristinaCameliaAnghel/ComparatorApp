package etti.comparator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping ("/servicii")
    public String loadServiciiPage(){
        return "servicii";
    }
    @RequestMapping ("/utilitati")
    public String loadUtilitatiPage(){
        return "utilitati";
    }

}
