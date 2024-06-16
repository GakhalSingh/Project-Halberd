import halberd.ai.AI;
import halberd.ai.OllamaModel;
import halberd.ai.OnlinellamaModel;
import halberd.sound.audio;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ChatBox extends Observable {
    private List<ChatMessage> messages;
    private final AI model;
    private final audio notificationSound;


    private static AI aiFactory() {
        boolean netAccess = false;
        try {
            Socket socket = new Socket("www.google.com", 80);
            netAccess = socket.isConnected();
            socket.close();
        } catch (IOException ignore) {

        }
        if (netAccess) {
            return new OnlinellamaModel();
        } else {
            return new OllamaModel();
        }
    }

    public ChatBox(List<ChatMessage> messages) {
        this.messages = messages;
        this.model = aiFactory();
        this.notificationSound = new audio();
    }


    public String generateResponse(String message) {
        String response = model.chat(message);
        notificationSound.playSound();
        return response;
    }
}
