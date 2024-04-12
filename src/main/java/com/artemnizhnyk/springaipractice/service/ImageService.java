package com.artemnizhnyk.springaipractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageClient imageClient;

    public String getPictureUrl() {
        ImageOptions imageOptions = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .withModel(OpenAiImageApi.ImageModel.DALL_E_3.getValue())
                .build();

        ImagePrompt imagePrompt = new ImagePrompt("Paint programmer in a dark forrest", imageOptions);
        return imageClient.call(imagePrompt)
                .getResult()
                .getOutput()
                .getUrl();
    }
}
