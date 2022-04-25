package edu.school21.chat.models;

import java.util.List;

public class User {
    private long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> rooms;

    public User(long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> rooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.rooms = rooms;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (createdRooms != null ? !createdRooms.equals(user.createdRooms) : user.createdRooms != null) return false;
        return rooms != null ? rooms.equals(user.rooms) : user.rooms == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createdRooms != null ? createdRooms.hashCode() : 0);
        result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms=" + createdRooms +
                ", rooms=" + rooms +
                '}';
    }
}
