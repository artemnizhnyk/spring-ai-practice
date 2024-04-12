package com.artemnizhnyk.springaipractice.web.controller;

import com.artemnizhnyk.springaipractice.service.FileReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/ai")
@RestController
public class FileReaderController {

    private final FileReaderService fileReaderService;

    @GetMapping("/docx")
    String getResponseFromDocx() {
        return fileReaderService.getResponseFromDocx();
    }

    @GetMapping("/pdf")
    String getResponseFromPdf() {
        return fileReaderService.getResponseFromPdf();
    }
}
