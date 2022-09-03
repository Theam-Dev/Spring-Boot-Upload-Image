package com.example.springbootupload.controller;

import com.example.springbootupload.model.ImageEntity;
import com.example.springbootupload.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ImageController {
    @Autowired
    private ImageService postService;
    public static String uploadDirectory = "src/main/resources/static/upload";

    @GetMapping("/")
    public String Index(Model model) {
        Iterable<ImageEntity> post = postService.getAll();
        model.addAttribute("post",post);
        return "image/index";
    }
    @GetMapping("/create")
    public String Create(Model model) {
        ImageEntity post = new ImageEntity();
        model.addAttribute("post",post);
        model.addAttribute("formtitle","Create Post");
        model.addAttribute("button","Create");
        return "image/form";
    }
    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable Long id, Model model) {
        ImageEntity post = postService.getById(id);
        model.addAttribute("post", post);
        model.addAttribute("formtitle","Update Post");
        model.addAttribute("button","Update");
        return "image/form";
    }
    @PostMapping("/save")
    public String Save(@RequestParam("img") MultipartFile file, ImageEntity post) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime sedateness = LocalDateTime.now();
        String date = dtf.format(sedateness);
        String filename = date+file.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory,filename);
        try {
            Files.write(filePath,file.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
        post.setImage(filename);
        postService.saveData(post);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable (value = "id") Long id) {
        try {
            postService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}

