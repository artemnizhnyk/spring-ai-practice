package com.artemnizhnyk.springaipractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileReaderService {

    private final ChatClient chatClient;

    public String getResponseFromDocx() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader("classpath:firma.docx");

        String userPrompt = "What's the aim of the firm";

        Message userMessage = new UserMessage(userPrompt);

        String systemPrompt = tikaDocumentReader.get().stream()
                .map(Document::getContent)
                .collect(Collectors.joining());

        Message systemMessage = new SystemPromptTemplate(systemPrompt)
                .createMessage();

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        return chatClient.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }

    public String getResponseFromPdf() {
        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                        .withNumberOfBottomTextLinesToDelete(0)
                        .build())
                .withPagesPerDocument(1)
                .build();

        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader("classpath:firma.pdf", config);

        String userPrompt = "What's the aim of the firm";

        Message userMessage = new UserMessage(userPrompt);

        TextSplitter tokenTextSplitter = new TokenTextSplitter();
        List<Document> documents = tokenTextSplitter.apply(pagePdfDocumentReader.get());

        String systemPrompt = """
                Documentation: {documents}
                """;

        Message systemMessage = new SystemPromptTemplate(systemPrompt)
                .createMessage(Map.of("documents", documents));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        return chatClient.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }
}
