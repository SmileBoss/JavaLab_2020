package ru.itis.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/homework_1";
    private static final String USER = "postgres";
    private static final String PASS = "*********";

    public static void main(String[] args) {
        SimpleDataSource dataSource = new SimpleDataSource();
        Connection connection = dataSource.openConnection(URL, USER, PASS);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from student");

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("First Name: " + resultSet.getString("first_name"));
                System.out.println("Last Name: " + resultSet.getString("last_name"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Group Number: " + resultSet.getInt("group_number"));
            }
            System.out.println("-------------------------------");
            resultSet = statement.executeQuery("select s.id as s_id from student s full outer join mentor m on s.id = m.student_id");
            while (resultSet.next()) {
                System.out.println("ID" + resultSet.getInt("s_id"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
