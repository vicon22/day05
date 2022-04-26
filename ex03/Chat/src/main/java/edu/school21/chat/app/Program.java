package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException

    {
        Scanner scanner = new Scanner(System.in);


        final String USER_NAME = "postgres";
        final String PASSWORD = "postgres";
        final String URL = "jdbc:postgresql://localhost/postgres";
//        final String DB_SCHEMA = "/Users/eveiled/Desktop/day05/ex01/Chat/src/main/resources/schema.sql";
//        final String DB_DATA = "/Users/eveiled/Desktop/day05/ex01/Chat/src/main/resources/data.sql";
        final String DB_SCHEMA = "src/main/resources/schema.sql";
        final String DB_DATA = "src/main/resources/data.sql";
        Connection connection;
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(PASSWORD);

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        runQueriesFromFile(dataSource.getConnection(), DB_SCHEMA);
        runQueriesFromFile(dataSource.getConnection(), DB_DATA);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        ChatroomsRepository chatroomsRepository = new ChatroomsRepositoryJdbcImpl(dataSource, usersRepository);
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource,usersRepository,chatroomsRepository);

        System.out.println("-----------------------------------");
        Message message = messagesRepository.findById(1L).orElse(null);
        System.out.println(message);
        System.out.println("-----------------------------------");

        assert message != null;
        message.setText("update");
        messagesRepository.update(message);
        System.out.println("-----------------------------------");
        message = messagesRepository.findById(1L).orElse(null);
        System.out.println(message);

    }

    private static void check(Connection connection) throws SQLException{

        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        ResultSet resultSet = statement.executeQuery( "SELECT * FROM chat.messages");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("message_id") + " " + resultSet.getInt("message_author") + " " + resultSet.getInt("message_room") + " " + resultSet.getTimestamp("message_date"));
        }

    }

    private static void runQueriesFromFile(Connection connection, String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(
                new File(filename)).useDelimiter(";");
        try {
            while (scanner.hasNext()) {
                connection.createStatement().execute(scanner.next().trim());
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        scanner.close();
    }
}