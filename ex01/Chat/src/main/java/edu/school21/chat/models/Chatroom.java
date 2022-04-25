package edu.school21.chat.models;

import java.util.List;

public class Chatroom {

    private long id;
    private String name;
    private User owner;
    private List<Message> roomsMessages;

    public Chatroom(long id, String name, User owner, List<Message> roomsMessages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.roomsMessages = roomsMessages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Message> getRoomsMessages() {
        return roomsMessages;
    }

    public void setRoomsMessages(List<Message> roomsMessages) {
        this.roomsMessages = roomsMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chatroom chatroom = (Chatroom) o;

        if (id != chatroom.id) return false;
        if (name != null ? !name.equals(chatroom.name) : chatroom.name != null) return false;
        if (owner != null ? !owner.equals(chatroom.owner) : chatroom.owner != null) return false;
        return roomsMessages != null ? roomsMessages.equals(chatroom.roomsMessages) : chatroom.roomsMessages == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (roomsMessages != null ? roomsMessages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", roomsMessages=" + roomsMessages +
                '}';
    }
}
