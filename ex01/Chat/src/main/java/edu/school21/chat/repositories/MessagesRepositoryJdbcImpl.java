package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private Connection connection;
    private String QUERY_TEMPLATE = "SELECT * FROM chat.messages WHERE message_id=?";
    private String SAVE_QUERY_TEMPLATE = "INSERT INTO chat.messages (message_author, message_room, message_text, message_date) VALUES (?,?,?,?) RETURNING *";
    private String UPDATE_QUERY_TEMPLATE = "UPDATE chat.messages SET message_author = ?, message_room = ?, message_text = ?, message_date = ? WHERE message_id = ? RETURNING *;";
    private UsersRepository usersRepository;
    private ChatroomsRepository chatroomsRepository;

    public MessagesRepositoryJdbcImpl(Connection connection, UsersRepository usersRepository, ChatroomsRepository chatroomsRepository) {
        this.connection = connection;
        this.usersRepository = usersRepository;
        this.chatroomsRepository = chatroomsRepository;
    }

    @Override
    public Optional<Message> findById(Long id) {

        Message ret = null;
        ResultSet resultSet = null;

        try {
            PreparedStatement query = connection.prepareStatement(QUERY_TEMPLATE);
            query.setLong(1, id);
            resultSet = query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet.next()){
                ret = new Message(
                        resultSet.getLong("message_id"),
                        usersRepository.findById(resultSet.getLong("message_author")).orElse(null),
                        chatroomsRepository.findById(resultSet.getLong("message_room")).orElse(null),
                        resultSet.getString("message_text"),
                        resultSet.getTimestamp("message_date").toLocalDateTime()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(ret);
    }

    @Override
    public void save(Message message) {

        ResultSet resultSet = null;

        if (!usersRepository.findById(message.getAuthor().getId()).isPresent()){
            throw new NotSavedSubEntityException("Author not found");
        }
        if (chatroomsRepository.findById(message.getChatroom().getId()).isEmpty()){
            throw new NotSavedSubEntityException("Chatroom not found");
        }
        try {
            PreparedStatement query = connection.prepareStatement(SAVE_QUERY_TEMPLATE);
            query.setLong(1, message.getAuthor().getId());
            query.setLong(2, message.getChatroom().getId());
            query.setString(3, message.getText());
            query.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            resultSet = query.executeQuery();
            resultSet.next();
            message.setId(resultSet.getLong("message_id"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(Message message) {
        ResultSet resultSet = null;


        if (findById(message.getId()).isEmpty()) {
            throw new NotSavedSubEntityException("Message with id=" + message.getId() + " not found");
        }
        try {
            PreparedStatement query = connection.prepareStatement(UPDATE_QUERY_TEMPLATE);
            query.setLong(1, message.getAuthor().getId());
            query.setLong(2, message.getChatroom().getId());
            query.setString(3, message.getText());
            query.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            query.setLong(5, message.getId());
            resultSet = query.executeQuery();
            resultSet.next();
            message.setId(resultSet.getLong("message_id"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
