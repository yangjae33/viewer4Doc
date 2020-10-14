package com.yangql.viewer4doc.interfaces;

import com.google.common.net.HttpHeaders;
import com.yangql.viewer4doc.application.AdminFileService;
import com.yangql.viewer4doc.domain.AdminFile;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/group")
public class AdminFileController {

    //public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";
    public final static String UPLOAD_DIR = System.getProperty("user.dir") + "/adminfiles/";
    @Autowired
    private AdminFileService adminFileService;

    @GetMapping("/")
    public ModelAndView homepage(){
        List<AdminFile> adminFileList = adminFileService.getFiles();
        ModelAndView mv = new ModelAndView("index.html");
        mv.addObject("fileList",adminFileList);
        return mv;
    }
    @GetMapping("/class")
    public ModelAndView classpage(){
        List<AdminFile> adminFileList = adminFileService.getFileByPubId(1L);
        ModelAndView mv = new ModelAndView("index2.html");
        mv.addObject("fileList",adminFileList);
        return mv;
    }

    @GetMapping("/web/files")
    public String filelist(){
        return "list";
    }

    @PostMapping("/web/upload")
    public String uploadFile(
            @RequestPart("file") MultipartFile file, RedirectAttributes attributes
    ) throws IOException {
        String savePath = System.getProperty("user.dir") + "/adminfiles/";

        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        if(file.isEmpty()){
            attributes.addFlashAttribute("message","no file to upload");
            return "redirect:/";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = fileName.replaceAll(" ","_");
        try{
            Path path = Paths.get(UPLOAD_DIR+fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            e.printStackTrace();
        }
        attributes.addFlashAttribute("message","file uploaded successfully");
        AdminFile adminFile = AdminFile.builder()
                .name(fileName)
                .build();
        adminFileService.addFile(adminFile);

        return "redirect:/";
    }

    @GetMapping("/web/thymeleaf")
    public String thymeleafTest2(Model model){
        TestVo testModel = new TestVo("jaehyuk","yang");
        model.addAttribute("testModel",testModel);
        return "thymeleafTest";
    }

    @GetMapping("/web/download/{fileId}")
    public ResponseEntity<Resource> webDownloadFile(
            @PathVariable("fileId") Long fileId
    ) throws FileNotFoundException {
        Resource resource = adminFileService.loadAsResource(fileId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;" +
                        "filename=\""+resource.getFilename()+"\"").body(resource);
    }

    @GetMapping("/files")
    public List<FileInfo> getFilesAPI(

    ){
        List<FileInfo> fileInfos = new ArrayList<>();

        return fileInfos;
    }
}
