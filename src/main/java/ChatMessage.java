public class ChatMessage {
    private int chatnum;
    private String sender;
    private String message;

    public ChatMessage(String chatnum, String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public int getChatnum() {
        return chatnum;
    }

    public void setChatnum(int chatnum) {
        this.chatnum = chatnum;
    }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
