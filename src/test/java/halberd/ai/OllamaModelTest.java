package halberd.ai;

import static org.junit.jupiter.api.Assertions.*;

class OllamaModelTest {

    @org.junit.jupiter.api.Test
    void chat() {
        OllamaModel model = new OllamaModel();
        String response = model.chat("Hello");
        System.out.println(response);
        assertNotNull(response);
    }
}