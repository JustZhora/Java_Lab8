package bsu.rfe.java.group8.lab8.zhodik.var6A.entity;

public class ChatMessage {

    private String message;
    private ChatUser author;
    private long timestamp;
    private String privatem;//

    public ChatMessage(String message, ChatUser author, long timestamp, String pname) {
        this.message = message;
        this.author = author;
        this.timestamp = timestamp;
        this.privatem = pname;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatUser getAuthor() {
        return author;
    }

    public void setAuthor(ChatUser author) {
        this.author = author;
    }

    public void setPrivatem(String privatem) {
        this.privatem = privatem;
    }

    public String getPrivatem() {
        return privatem;
    }

}
