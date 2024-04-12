package com.artemnizhnyk.springaipractice.web.controller;

import com.artemnizhnyk.springaipractice.service.ChatService;
import com.artemnizhnyk.springaipractice.web.dto.StoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/ai")
@RestController
class ChatController {

    private final ChatService chatService;

    @GetMapping("/joke")
    String getJoke() {
        return chatService.getJoke();
    }

    @GetMapping("/famous-person")
    String getFamousPersonName() {
        return chatService.getFamousPersonName();
    }

    @GetMapping("/story")
    StoryDto getStory() {
        return chatService.getStory();
    }
}
