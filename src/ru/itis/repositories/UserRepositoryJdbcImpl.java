package ru.itis.repositories;

import ru.itis.models.Mentor;
import ru.itis.models.Student;

import java.nio.file.LinkOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbcImpl implements UserRepository {
    //Language SQL
    private static final String SQL_SELECT_BY_ID = "select * from student where id =";
    private static final String SQL_INSERT_STUDENT = "insert into student (first_name, last_name, age, group_number)" +
            "values (?, ?, ?, ?)";
    private static final String SQL_UPDATE_STUDENT = "update student set ";
    private static final String SQL_SELECT_BY_AGE = "select * from student where age =";
    private static final String SQL_SELECT_ALL = "select s.id as s_id, s.first_name as s_first_name," +
            "s.last_name as s_last_name, s.age as s_age," +
            "s.group_number as s_group_number, m.id as m_id," +
            "m.first_name as m_first_name, m.last_name as m_last_name" +
            " from mentor m full outer join student s on m.student_id = s.id";


    private Connection connection;

    public UserRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Student> findAllByAge(int age) {
        Statement statement = null;
        ResultSet resultSet = null;

        try{
         statement = connection.createStatement();
         resultSet = statement.executeQuery(SQL_SELECT_BY_AGE + age);
         List<Student> students = new ArrayList<>();
         int i = 0;
            while (resultSet.next()){

                students.add(new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("group_number")
                ));
            }
            if (students.size() != 0){
                return students;
            } else {
                return null;
            }

        }catch (SQLException e){
            throw new IllegalArgumentException();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
        }
    }

    @Override
    public List<Student> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Student student = new Student(resultSet.getLong("s_id"),
                        resultSet.getString("s_first_name"), resultSet.getString("s_last_name"),
                        resultSet.getInt("s_age"), resultSet.getInt("s_group_number"));
                int index = students.indexOf(student);
                if (index >= 0) {
                    students.get(index).getMentors().add(new Mentor(resultSet.getLong("m_id"),
                            resultSet.getString("m_first_name"), resultSet.getString("m_last_name")));
                } else {
                    student.setMentors(new ArrayList<>());
                    student.getMentors().add(new Mentor(resultSet.getLong("m_id"),
                            resultSet.getString("m_first_name"), resultSet.getString("m_last_name")));
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
        return students;
    }

    @Override
    public Student findById(long id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_BY_ID + id);

            if (resultSet.next()) {
                return new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("group_number")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
        }
    }

    @Override
    public void save(Student entity) {
        Statement statement = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENT,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setInt(4, entity.getGroupNumber());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    resultSet.next();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    @Override
    public void update(Student entity) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeQuery(SQL_UPDATE_STUDENT
                    + "first_name = '"
                    + entity.getFirstName() + "', "
                    + "last_name = '"
                    + entity.getFirstName() + "', "
                    + "age = '"
                    + entity.getAge() + "', "
                    + "group_number = '"
                    + entity.getGroupNumber()
                    + "' where id = '" + entity.getId() + "';");

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
        }
    }
}
