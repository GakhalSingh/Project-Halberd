package halberd.ai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class OllamaModel implements AI{

    private OllamaChatModel model;

    public OllamaModel() {
        this.model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma")
                .build();
    }

    public String chat(String userMessage) {
        String response = model.generate(userMessage);
        return response;
    }
}
