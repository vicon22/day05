package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;

public class ChatroomsRepositoryJdbcImpl implements ChatroomsRepository {

    private Connection connection;
    private String QUERY_TEMPLATE = "SELECT * FROM chat.chatrooms WHERE chatroom_id=?";
    private UsersRepository usersRepository;

    public ChatroomsRepositoryJdbcImpl(Connection connection, UsersRepository usersRepository) {
        this.connection = connection;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<Chatroom> findById(Long id) {

        Chatroom ret = null;
        ResultSet resultSet = null;
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        try {
            PreparedStatement query = connection.prepareStatement(QUERY_TEMPLATE);
            query.setLong(1, id);
            resultSet = query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet.next()){
                ret = new Chatroom(
                        resultSet.getLong("chatroom_id"),
                        resultSet.getString("chatroom_name"),
                        usersRepository.findById(resultSet.getLong("chatroom_owner")).orElse(null),
                        null
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(ret);
    }

}
