package model;

import utils.DataConnectionConfigure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//repository or DAO - data access object
public class UserDao {
    //create methods
    public List<User> findAll(){
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        Connection connection = DataConnectionConfigure.getConnection();
        if (connection == null) {
            System.out.println("Failed to get database connection - connection is null");
            return users;
        }
        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String uuid = resultSet.getString("uuid");
                String userName = resultSet.getString("user_name");
                String email  =resultSet.getString("email");
                String password = resultSet.getString("password");
                String profile  = resultSet.getString("profile");
                User user = new User(id,uuid,userName,email,password,profile);
                users.add(user);
            }
        }catch (Exception exception){
            System.out.println("Connection failed: " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
        return users;
    }
    public int remove(User user){
        String sql = "DELETE FROM users WHERE id = ?";
        Connection connection = DataConnectionConfigure.getConnection();
        if (connection == null) {
            System.out.println("Failed to get database connection - connection is null");
            return 0;
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            return preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println("Connection Failed: " + exception.getMessage());
            exception.printStackTrace();
            return 0;
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
    public List<User> findByName(String name) {
        String sql = "SELECT * FROM users WHERE user_name ILIKE ?";
        List<User> users = new ArrayList<>();
        Connection connection = DataConnectionConfigure.getConnection();
        if (connection == null) {
            System.out.println("Failed to connection database");
            return users;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String uuid = resultSet.getString("uuid");
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String profile = resultSet.getString("profile");
                users.add(new User(id, uuid, userName, email, password, profile));
            }
        } catch (Exception exception) {
            System.out.println("Search failed: " + exception.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
        return users;
    }

    public User update(User uu){
        String sql = "UPDATE users SET user_name = ?, email = ?, password = ?, profile = ? WHERE id = ?";
        Connection connection = DataConnectionConfigure.getConnection();
        if (connection == null) {
            System.out.println("Failed to get database connection - connection is null");
            return uu;
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uu.getName());
            preparedStatement.setString(2, uu.getEmail());
            preparedStatement.setString(3, uu.getPassword());
            preparedStatement.setString(4, uu.getProfile());
            preparedStatement.setInt(5, uu.getId());
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println("Connection Failed: " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
        return uu;
    }
    public User save(User user) {
        String sql = """
                INSERT INTO users (uuid, user_name, email, password, profile) 
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection connection = DataConnectionConfigure.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUuid());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getProfile());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected <= 0) {
                throw new RuntimeException("Failed to insert new data into table users");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
            return user;
        } catch (Exception exception) {
            System.out.println("Connection Failed: " + exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }

}



