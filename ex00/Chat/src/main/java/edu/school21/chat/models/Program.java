package edu.school21.chat.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException

    {
        final String USER_NAME = "postgres";
        final String PASSWORD = "postgres";
        final String URL = "jdbc:postgresql://localhost/postgres";
        final String DB_SCHEMA = "/Users/eveiled/Desktop/day05/ex00/Chat/src/main/resources/schema.sql";
        final String DB_DATA = "/Users/eveiled/Desktop/day05/ex00/Chat/src/main/resources/data.sql";
        Statement statement;
        Connection connection;

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        runQueriesFromFile(connection, DB_SCHEMA);
        runQueriesFromFile(connection, DB_DATA);
        check(statement);
    }

    private static void check(Statement statement) throws SQLException{

        ResultSet resultSet = statement.executeQuery( "SELECT * FROM chat.users");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("user_id") + " " + resultSet.getString("user_login") + " " + resultSet.getString("user_password") + " " + resultSet.getString("created_rooms"));
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