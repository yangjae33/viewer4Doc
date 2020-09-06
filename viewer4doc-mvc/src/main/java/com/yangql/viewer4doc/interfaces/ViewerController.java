package com.yangql.viewer4doc.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewerController {

    @GetMapping("/viewer4doc")
    public String showViewer(
            @RequestParam Long fileId
    ){
        String fileName = "";
        String url = "viewer2.html?file="+fileName;
        //return url;
        return "viewer2.html";
    }
}
