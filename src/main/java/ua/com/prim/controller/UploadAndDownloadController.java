package ua.com.prim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.prim.entity.MyUser;
import ua.com.prim.service.MyUserServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadAndDownloadController {

    @Autowired
    private MyUserServiceImpl myUserServiceImpl;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload/{type}")
    public String uploadFile(@PathVariable("type") String type,
                             @RequestParam("file") MultipartFile file, Model model) {
        MyUser currentUser = getCurrentUser();
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + resultFileName));
                if (type.equals("music")) currentUser.getMusic().add(resultFileName);
                if (type.equals("video")) currentUser.getVideos().add(resultFileName);
                if (type.equals("image")) currentUser.getImages().add(resultFileName);
                if (type.equals("mainPage") || type.equals("settingUser")) currentUser.setMainPhoto("/img/" + resultFileName);
                myUserServiceImpl.save(currentUser);
            } catch (IOException e) {
                model.addAttribute("haveFile", true);
                e.printStackTrace();
            }
        }
        File folder = new File(uploadPath);
        File[] listOfFiles = folder.listFiles();
        model.addAttribute("files", listOfFiles);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("successfulLoading", true);
        return type;
    }

    @RequestMapping("/file/{fileName}")
    @ResponseBody
    public void show(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        if (fileName.indexOf(".doc") > -1) response.setContentType("application/msword");
        if (fileName.indexOf(".docx") > -1) response.setContentType("application/msword");
        if (fileName.indexOf(".xls") > -1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".csv") > -1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".ppt") > -1) response.setContentType("application/ppt");
        if (fileName.indexOf(".pdf") > -1) response.setContentType("application/pdf");
        if (fileName.indexOf(".zip") > -1) response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; fileName=" + fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(uploadPath + fileName);
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            bos.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MyUser getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserServiceImpl.findByLogin(user.getUsername());
    }
}
