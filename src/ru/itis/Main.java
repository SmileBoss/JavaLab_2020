package ru.itis;

import ru.itis.jdbc.SimpleDataSource;
import ru.itis.models.Student;
import ru.itis.repositories.UserRepository;
import ru.itis.repositories.UserRepositoryJdbcImpl;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/homework_1";
    private static final String USER = "postgres";
    private static final String PASS = "7272An358n";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASS);
        UserRepository studentRepository = new UserRepositoryJdbcImpl(connection);
        Student studentTest = new Student(null, "Max", "Blatov", 24, 212);
//        studentRepository.save(studentTest);
//        System.out.println(studentRepository.findById(25L));
////        studentTest.setFirstName("Alex");
////        studentTest.setAge(26);
//        System.out.println("Age : " + studentTest.getAge() + ", Name : " + studentTest.getFirstName() + ", ID : " + studentTest.getId());
//        System.out.println("------------------------");
////        studentRepository.update(studentTest);
//        System.out.println(studentRepository.findById(25L));
        System.out.println(studentRepository.findAllByAge(20));
        connection.close();

    }
}
