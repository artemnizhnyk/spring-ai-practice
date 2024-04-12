package com.artemnizhnyk.springaipractice.service;

import com.artemnizhnyk.springaipractice.web.dto.StoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatClient chatClient;

    public String getJoke() {
        return chatClient.call("Write joke about programmers");
    }

    public String getFamousPersonName() {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withTemperature(0.8F)
                .withModel(OpenAiApi.ChatModel.GPT_3_5_TURBO.getValue())
                .build();
        String promptString = "Write a name of one famous person";
        Prompt prompt = new Prompt(promptString, chatOptions);
        return chatClient.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }

    public StoryDto getStory() {
        String prompt = """
                History in polish language on topic - {topic}{format}
                """;

        BeanOutputParser<StoryDto> storyDtoBeanOutputParser = new BeanOutputParser<>(StoryDto.class);

        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        promptTemplate.add("topic", "funny programming day");
        promptTemplate.add("format", "funny programming day");
        promptTemplate.add("format", storyDtoBeanOutputParser.getFormat());
        promptTemplate.setOutputParser(storyDtoBeanOutputParser);

        String content = chatClient.call(promptTemplate.create())
                .getResult()
                .getOutput()
                .getContent();

        return storyDtoBeanOutputParser.parse(content);
    }
}
