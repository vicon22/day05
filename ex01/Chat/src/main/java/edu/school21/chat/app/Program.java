package edu.school21.chat.app;

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
        final String DB_SCHEMA = "/Users/eveiled/Desktop/day05/ex01/Chat/src/main/resources/schema.sql";
        final String DB_DATA = "/Users/eveiled/Desktop/day05/ex01/Chat/src/main/resources/data.sql";
        Connection connection;

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        runQueriesFromFile(connection, DB_SCHEMA);
        runQueriesFromFile(connection, DB_DATA);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(connection);
        ChatroomsRepository chatroomsRepository = new ChatroomsRepositoryJdbcImpl(connection, usersRepository);
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(connection,usersRepository,chatroomsRepository);


        //System.out.println("Put message_id:");
        //System.out.println(messagesRepository.findById(scanner.nextLong()).orElse(null));
        check(connection);
        System.out.println("-----------------------------------");

        Message message = new Message(usersRepository.findById(1L).orElse(null), chatroomsRepository.findById(1L).orElse(null), "This message has to be saved");

        messagesRepository.save(message);

        check(connection);
        System.out.println("-----------------------------------");
        System.out.println(message.getId());

    }

    private static void check(Connection connection) throws SQLException{

        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

//        ResultSet resultSet = statement.executeQuery( "SELECT * FROM chat.users");
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("user_id") + " " + resultSet.getString("user_login") + " " + resultSet.getString("user_password") + " " + resultSet.getString("created_rooms"));
//        }
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