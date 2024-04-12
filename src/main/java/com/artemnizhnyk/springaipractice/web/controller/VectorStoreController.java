package com.artemnizhnyk.springaipractice.web.controller;

import com.artemnizhnyk.springaipractice.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/ai")
@RestController
public class VectorStoreController {

    private final VectorStoreService vectorStoreService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/add-data")
    void addDataToVectorStore() {
        vectorStoreService.loadDocumentsToVectorStore();
    }

    @GetMapping("/test-vector-store")
    String testVectorStore() {
        return vectorStoreService.testVectorDB();
    }
}
