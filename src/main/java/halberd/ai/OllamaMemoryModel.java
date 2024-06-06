package halberd.ai;


import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public class OllamaMemoryModel implements AI {

    private AI assistant;
    private ChatMemory chatMemory;

    public OllamaMemoryModel(){
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        this.assistant = AiServices.builder(AI.class)
                .chatLanguageModel(OllamaChatModel.builder()
                        .baseUrl("http://localhost:11434")
                        .modelName("gemma")
                        .build())
                .chatMemory(chatMemory)
                .build();
    }

    public String chat(String userMessage) {
        return assistant.chat(userMessage);
    }
}