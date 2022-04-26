package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class ChatroomsRepositoryJdbcImpl implements ChatroomsRepository {

    private DataSource dataSource;
    private String QUERY_TEMPLATE = "SELECT * FROM chat.chatrooms WHERE chatroom_id=?";
    private UsersRepository usersRepository;

    public ChatroomsRepositoryJdbcImpl(DataSource dataSource, UsersRepository usersRepository) {
        this.dataSource = dataSource;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<Chatroom> findById(Long id) {

        Chatroom ret = null;
        ResultSet resultSet = null;
        Statement statement;
        Connection connection;
        try {
            connection = dataSource.getConnection();
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
