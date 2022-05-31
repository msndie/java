package edu.school21.sockets.models;

import java.sql.Timestamp;

public class Message {
    private Long id;
    private String text;
    private Long authorId;
    private Timestamp time;

    public Message(Long id, Long authorId, String text, Timestamp time) {
        this.id = id;
        this.text = text;
        this.authorId = authorId;
        if (time == null) {
            this.time = new Timestamp(System.currentTimeMillis());
        } else {
            this.time = time;
        }
    }

    public Timestamp getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", authorId=" + authorId +
                ", time=" + time +
                '}';
    }
}
