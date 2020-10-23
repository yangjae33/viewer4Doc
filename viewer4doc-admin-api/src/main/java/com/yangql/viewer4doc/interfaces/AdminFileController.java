package com.yangql.viewer4doc.interfaces;

import com.google.common.net.HttpHeaders;
import com.yangql.viewer4doc.application.*;
import com.yangql.viewer4doc.domain.*;
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
import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/view")
public class AdminFileController {

    //public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";
    public final static String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    @Autowired
    private AdminFileService adminFileService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public ModelAndView homepage(){
        List<AdminFile> adminFileList = adminFileService.getFiles();
        ModelAndView mv = new ModelAndView("index.html");
        mv.addObject("fileList",adminFileList);
        return mv;
    }
    @GetMapping("")
    public ModelAndView mainPage(){
        ModelAndView mv = new ModelAndView("admin/index.html");
        return mv;
    }
    @GetMapping("/home")
    public ModelAndView homePage(){
        ModelAndView mv = new ModelAndView("admin/home.html");
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
        String savePath = System.getProperty("user.dir") + "/uploads/";

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
            return "redirect:/view/files";
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
        FileInfo fileInfo = FileInfo.builder()
                .name(fileName)
                .build();
        fileService.addFile(fileInfo);

        return "redirect:/view/files";
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
    public ModelAndView getFilesAPI(

    ){
        List<FileInfo> fileList = fileService.getFiles();
        ModelAndView mv = new ModelAndView("admin/fileList.html");
        mv.addObject("fileList",fileList);
        return mv;
    }
    @GetMapping("/users")
    public ModelAndView getUsersAPI(
        ModelAndView m
    ){
        m.setViewName("admin/userList.html");
        List<UserInfo> userList = userService.getUsers();
        //ModelAndView mv = new ModelAndView("admin/userList.html");
        m.addObject("userList",userList);
        UserInfo u = UserInfo.builder()
                .email("")
                .name("")
                .password("")
                .build();
        m.addObject("userInfo",u);
        return m;
    }
    @PostMapping("/users")
    public String addUserAPI(
            @RequestParam("email") String email,
            @RequestParam("name") String userName,
            @RequestParam("password") String password,
            @RequestParam("level") Long level,
            ModelAndView m
    ){
        UserInfo u = userService.registerUserAddLevel(email,userName,password,level);
        m.addObject("userInfo",u);
        return "redirect:/view/users";
    }
    @PostMapping("/users/delete")
    public String deleteUserAPI(
            @RequestParam("id") Long id,
            ModelAndView m
    ){
        userService.deactivateUser(id);

        return "redirect:/view/users";
    }
    @GetMapping("/users/{id}")
    public ModelAndView getUserAPI(
            @PathVariable("id") Long id
    ){
        UserInfo userInfo = userService.getUser(id);
        ModelAndView mv = new ModelAndView("admin/userDetail.html");
        mv.addObject("userInfo",userInfo);
        return mv;
    }
    @GetMapping("/shares")
    public ModelAndView getSharesAPI(

    ){
        List<Share> shareList = shareService.getShares();
        ModelAndView mv = new ModelAndView("admin/shareList.html");
        mv.addObject("shareList",shareList);
        return mv;
    }
    @GetMapping("/groups")
    public ModelAndView getGroupsAPI(

    ){
        List<GroupInfo> groupList = groupService.getGroups();
        ModelAndView mv = new ModelAndView("admin/groupList.html");
        mv.addObject("groupList",groupList);
        return mv;
    }

}
