package se.sprinto.hakan.chatapp.model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    //använder endast userId här, eftersom hela Usern inte behövs
    private int userId;
    private String text;
    private LocalDateTime timestamp;

    public Message(int userId, String text, LocalDateTime timestamp) {
        this.userId = userId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Message() {

    }

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(int anInt) {
    }

    public String getContent() {
        return "";
    }

    public Thread getUser() {
        return null;
    }

    public String getCreatedAt() {
        return "";
    }

    public void setContent(String content) {
    }

    public void setCreatedAt(LocalDateTime localDateTime) {
    }

    public void setUser(User u) {
    }
}

