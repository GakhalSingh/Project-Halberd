package halberd.ai;

import static org.junit.jupiter.api.Assertions.*;

class OllamaModelTest {

    @org.junit.jupiter.api.Test
    void chat() {
        AI model = new OllamaModel();
        String response = model.chat("Hello, today it's a sunny day outside");
        System.out.println(response);
        String response2 = model.chat("Do you remember my last message?");
        System.out.println(response2);
        assertNotNull(response);
    }
}