public class ChatMessage {
    private String sender;
    private String message;

    // Constructor, getters and setters
    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}