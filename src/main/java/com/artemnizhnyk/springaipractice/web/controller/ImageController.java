package com.artemnizhnyk.springaipractice.web.controller;

import com.artemnizhnyk.springaipractice.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/ai")
@RestController
public class ImageController {

    private final ImageService imageService;

    @SneakyThrows
    @GetMapping("/picture")
    void getPicture(final HttpServletResponse response) {
        response.sendRedirect(imageService.getPictureUrl());
    }
}
