package com.artemnizhnyk.springaipractice.config;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Bean
    VectorStore simpleVectorStore(final EmbeddingClient embeddingClient) {
        return new SimpleVectorStore(embeddingClient);
    }
}
