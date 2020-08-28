package com.yangql.viewer4doc.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewerController {

    @GetMapping("/viewer4doc")
    public String showViewer(){
        return "index.html";
    }
}
