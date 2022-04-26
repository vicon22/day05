package edu.school21.chat.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message {

    private long id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private LocalDateTime messageDateTime;

    public Message(long id, User author, Chatroom chatroom, String text, LocalDateTime messageDateTime) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.messageDateTime = messageDateTime;
    }

    public Message(User author, Chatroom chatroom, String text) {
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (author != null ? !author.equals(message.author) : message.author != null) return false;
        if (chatroom != null ? !chatroom.equals(message.chatroom) : message.chatroom != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return messageDateTime != null ? messageDateTime.equals(message.messageDateTime) : message.messageDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (chatroom != null ? chatroom.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (messageDateTime != null ? messageDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ",\nauthor=" + author +
                ",\nchatroom=" + chatroom +
                ",\ntext='" + text + '\'' +
                ",\nmessageDateTime=" + messageDateTime +
                '}';
    }
}