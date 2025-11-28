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

    public int getId() {
        return id;
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

    // --- setters ---

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

