package com.artemnizhnyk.springaipractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VectorStoreService {

    private final ChatClient chatClient;
    private final VectorStore simpleVectorStore;

    public String testVectorDB() {
        String userPrompt = "What's the aim of the firm";

        Message userMessage = new UserMessage(userPrompt);

        String systemPrompt = """
                Documentation: {documents}
                """;

        List<Document> similarDocuments = simpleVectorStore.similaritySearch(
                SearchRequest.query(userPrompt)
                        .withTopK(2)
        );

        Message systemMessage = new SystemPromptTemplate(systemPrompt)
                .createMessage(Map.of("documents", similarDocuments));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        return chatClient.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }

    public void loadDocumentsToVectorStore() {
        PagePdfDocumentReader documentReader = new PagePdfDocumentReader("classpath:firma.docx");

        TextSplitter tokenTextSplitter = new TokenTextSplitter();
        List<Document> documents = tokenTextSplitter.apply(documentReader.get());

        simpleVectorStore.add(documents);
    }
}
