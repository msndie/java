package edu.school21.chat.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String message;
    private Timestamp date;

    public Message(Long id, User author, Chatroom room, String message, Timestamp date) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.message = message;
        this.date = date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id == message1.id
                && Objects.equals(author, message1.author)
                && Objects.equals(room, message1.room)
                && Objects.equals(message, message1.message)
                && Objects.equals(date, message1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, message, date);
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return "Message{\n" +
                "id=" + id +
                ",\nauthor=" + author +
                ",\nroom=" + room +
                ",\nmessage='" + message + '\'' +
                ",\ndate=" + format.format(date) +
                '}';
    }
}
