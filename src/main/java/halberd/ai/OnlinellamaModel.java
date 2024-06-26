package halberd.ai;


import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class OnlinellamaModel implements AI {


    interface Assistant {
        String chat(String userMessage);
    }
    private final Assistant assistant;

    public OnlinellamaModel(){
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        this.assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(OllamaChatModel.builder()
                        .baseUrl("http://4.182.211.134:11434")
                        .modelName("llama3")
                        .build())
                .chatMemory(chatMemory)
                .build();
    }

    public String chat(String userMessage) {
        String response = assistant.chat(userMessage);
        return response;
    }
}