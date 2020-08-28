package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UploadFileService;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@CrossOrigin
@Controller
public class FileController {

    public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/")
    public String homepage(){
        return "index";
    }

    @PostMapping("/web/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file, RedirectAttributes attributes
    ) throws IOException {

        if(file.isEmpty()){
            attributes.addFlashAttribute("message","no file to upload");
            return "redirect:/";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            Path path = Paths.get(UPLOAD_DIR+fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            e.printStackTrace();
        }
        attributes.addFlashAttribute("message","file uploaded successfully");
        return "redirect:/";
    }

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFileWithResponseJson(
            @RequestParam("file") MultipartFile file
    ) throws IOException, URISyntaxException {
        String url = "/api/upload";

        FileInfo newfile = uploadFileService.uploadFile(file);

        return ResponseEntity.created(new URI(url)).body(newfile);
    }

    @GetMapping("/thymeleaf")
    public String thymeleafTest2(Model model){
        TestVo testModel = new TestVo("jaehyuk","yang");
        model.addAttribute("testModel",testModel);
        return "thymeleafTest";
    }
}
