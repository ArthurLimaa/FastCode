package br.com.fatcode.dao;

import br.com.fatcode.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    // Método para criar o usuário
    public void createUser(User user) throws SQLException {
        String SQL = "INSERT INTO USERS (USERNAME, EMAIL, BIRTHDATE, CPF, ADDRESS, CEP, PASSWORD, ROLE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setDate(3, java.sql.Date.valueOf(user.getBirthdate()));
            preparedStatement.setString(4, user.getCpf());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getCep());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getRole());

            preparedStatement.executeUpdate();
            System.out.println("User successfully inserted");

        } catch (SQLException e) {
            System.err.println("Error in database operation: " + e.getMessage());
            throw e;
        }
    }

    // Método para buscar o usuário por email (para o login)
    public User findUserByEmail(String email) throws SQLException {
        String SQL = "SELECT * FROM USERS WHERE EMAIL = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {

                if (rs.next()) {
                    return new User(
                            rs.getString("USERNAME"),
                            rs.getString("PASSWORD"),
                            rs.getString("EMAIL"),
                            rs.getDate("BIRTHDATE").toLocalDate(),
                            rs.getString("CPF"),
                            rs.getString("ADDRESS"),
                            rs.getString("CEP"),
                            rs.getString("ROLE")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
            throw e;
        }
        return null; // Retorna nulo se o usuário não for encontrado
    }

    // Método para buscar todos os usuários
    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM USERS";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getDate("BIRTHDATE").toLocalDate(),
                        rs.getString("CPF"),
                        rs.getString("ADDRESS"),
                        rs.getString("CEP"),
                        rs.getString("ROLE")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            throw e;
        }
        return users;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM USERS";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            // Iterar sobre o resultado da consulta
            while (rs.next()) {
                // Criar um objeto User com os dados do banco
                User user = new User(
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getDate("BIRTHDATE").toLocalDate(),
                        rs.getString("CPF"),
                        rs.getString("ADDRESS"),
                        rs.getString("CEP"),
                        rs.getString("ROLE")
                );

                // Adicionar o usuário à lista
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            // Você pode optar por lançar uma exceção customizada ou logar o erro de forma mais sofisticada
        }

        // Retorna a lista de usuários encontrados
        return users;
    }

}
