package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.GroupFileService;
import com.yangql.viewer4doc.application.GroupMemberService;
import com.yangql.viewer4doc.application.GroupService;
import com.yangql.viewer4doc.domain.FileInfo;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class GroupFileController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupFileService groupFileService;

    @GetMapping("/group/{groupId}/files")
    public ResponseEntity<?> getGroupFiles(
            Authentication authentication,
            @PathVariable("groupId") Long groupId
    ){
        List<FileInfo> groupFiles = groupFileService.getGroupFiles(groupId);
        return ResponseEntity.ok().body(groupFiles);
    }
}
