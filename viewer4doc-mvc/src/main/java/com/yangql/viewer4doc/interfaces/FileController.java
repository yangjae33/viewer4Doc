package com.yangql.viewer4doc.interfaces;

import com.google.common.net.HttpHeaders;
import com.yangql.viewer4doc.application.FileService;
import com.yangql.viewer4doc.domain.FileInfo;
import com.yangql.viewer4doc.domain.TestVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
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

    //public final static String UPLOAD_DIR = "/Users/mac/Desktop/uploads/";
    public final static String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    @Autowired
    private FileService fileService;

    @GetMapping("/web/")
    public String homepage(){
        return "index";
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

    @ApiOperation(
            value = "파일 업로드",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFileWithResponseJson(
            Authentication authentication,
            @RequestPart("file") MultipartFile file
    ) throws IOException, URISyntaxException {

        String savePath = System.getProperty("user.dir") + "/uploads/";

        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        String url = "/api/upload";
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);

        FileInfo newfile = fileService.uploadFile(file,userId);

        return ResponseEntity.created(new URI(url)).body(newfile);
    }
    @ApiOperation(
            value = "PDF파일로 변환",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/api/convert")
    public ResponseEntity<?> convertFile(
            @RequestParam("fileId") String fileId
    ){
        return null;
    }

    @ApiOperation(
            value = "업로드 및 PDF파일로 변환",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/api/upload-to-pdf")
    public ResponseEntity<?> uploadToPDF(
            Authentication authentication,
            @RequestPart(value = "file",required = true) MultipartFile file
    ) throws IOException, URISyntaxException {

        String savePath = System.getProperty("user.dir") + "/uploads/";

        if (!new File(savePath).exists()) {
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
        String convertPath = System.getProperty("user.dir") + "/converts/";

        if (!new File(convertPath).exists()) {
            try{
                new File(convertPath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);

        String url = "/api/upload-to-pdf";

        FileInfo newFile = fileService.uploadFileToPDF(file);

        return ResponseEntity.created(new URI(url)).body(newFile);
    }

    @GetMapping("/web/thymeleaf")
    public String thymeleafTest2(Model model){
        TestVo testModel = new TestVo("jaehyuk","yang");
        model.addAttribute("testModel",testModel);
        return "thymeleafTest";
    }

    @ApiOperation(
            value = "다운로드",
            httpMethod = "GET",
            produces = "application/json",
            consumes = "application/json",
            protocols = "http",
            responseHeaders = {}
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 403, message = "Access Token error")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @GetMapping("/api/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(
            Authentication authentication,
            @PathVariable("fileId") Long fileId
    ) throws FileNotFoundException {
        Claims claims = (Claims)authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);

        Resource resource = fileService.loadAsResource(fileId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;" +
                        "filename=\""+resource.getFilename()+"\"").body(resource);
    }
}
