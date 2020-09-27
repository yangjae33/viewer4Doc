package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.FileInfoRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
public class ViewerController {
    @Autowired
    private FileInfoRepository fileInfoRepository;

    @GetMapping("/web/viewer4doc")
    public String showViewer(
            @RequestParam Long fileId,
            Model model
    ){
        String fileName = "";
        FileInfo fileInfo = fileInfoRepository.findById(fileId).orElse(null);

        String url = "viewer2.html?file="+fileName;
        String pdfurl = "/pdfjs/web/pdftest.pdf";
        model.addAttribute("pdfurl",pdfurl);
        //return url;
        return "viewer2";
    }
}
