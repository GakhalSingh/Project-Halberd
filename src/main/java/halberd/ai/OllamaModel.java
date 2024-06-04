package halberd.ai;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class OllamaModel {

    private ChatLanguageModel model;

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
