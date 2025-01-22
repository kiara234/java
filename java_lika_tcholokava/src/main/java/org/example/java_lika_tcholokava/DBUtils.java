package org.example.java_lika_tcholokava;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bookstore", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException("Connection error: " + e.getMessage());
        }
    }

    public static void insert(Book book) {
        String sql = "INSERT INTO products (author, title, price, quantity, publisher) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, book.getPrice());
            preparedStatement.setInt(4, book.getQuantity());
            preparedStatement.setString(5, book.getPublisher());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Insert error: " + e.getMessage());
        }
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getInt("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("publisher")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query error: " + e.getMessage());
        }
        return books;
    }

    public static void update(Book book) {
        String sql = "UPDATE products SET author = ?, title = ?, price = ?, quantity = ?, publisher = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, book.getPrice());
            preparedStatement.setInt(4, book.getQuantity());
            preparedStatement.setString(5, book.getPublisher());
            preparedStatement.setInt(6, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Update error: " + e.getMessage());
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete error: " + e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Disconnection error: " + e.getMessage());
        }
    }
}
