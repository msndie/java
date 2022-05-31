package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.Objects;

public class Chatroom {
    private long id;
    private final String name;
    private final User owner;
    private ArrayList<Message> messages;

    public Chatroom(String name, User user) {
        this.name = name;
        owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id
                && Objects.equals(chatroom.name, name)
                && Objects.equals(chatroom.owner, owner)
                && Objects.equals(chatroom.messages, messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", messages=" + messages +
                '}';
    }
}
