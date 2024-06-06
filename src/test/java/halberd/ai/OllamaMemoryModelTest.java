package halberd.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OllamaMemoryModelTest {

    @org.junit.jupiter.api.Test
    void chat() {
        AI model = new OllamaMemoryModel();
        String response = model.chat("Hello, today it's a sunny day outside");
        System.out.println(response);
        String response2 = model.chat("Do you remember my last message?");
        System.out.println(response2);
        assertNotNull(response);
    }
}