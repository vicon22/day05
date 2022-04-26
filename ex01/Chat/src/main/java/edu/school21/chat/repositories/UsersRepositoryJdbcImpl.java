package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private DataSource dataSource;
    private String QUERY_TEMPLATE = "SELECT * FROM chat.users WHERE user_id=";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this. dataSource =  dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {

        User ret = null;
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
            resultSet = statement.executeQuery(QUERY_TEMPLATE + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet.next()){
                ret = new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("user_login"),
                        resultSet.getString("user_password"),
                        null,
                        null
                        );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(ret);
    }
}
