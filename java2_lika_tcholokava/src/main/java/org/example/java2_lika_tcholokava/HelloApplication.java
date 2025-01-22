package org.example.java2_lika_tcholokava;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class HelloApplication extends Application {
    private final ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private final PieChart pieChart = new PieChart(pieChartData);

    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Input Fields
        TextField idField = new TextField();
        TextField author = new TextField();
        TextField title = new TextField();
        TextField price = new TextField();
        TextField quantity = new TextField();
        TextField publisher = new TextField();

        gridPane.add(new Label("ID"), 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(new Label("Author"), 0, 1);
        gridPane.add(author, 1, 1);
        gridPane.add(new Label("Title"), 0, 2);
        gridPane.add(title, 1, 2);
        gridPane.add(new Label("Price"), 0, 3);
        gridPane.add(price, 1, 3);
        gridPane.add(new Label("Quantity"), 0, 4);
        gridPane.add(quantity, 1, 4);
        gridPane.add(new Label("Publisher"), 0, 5);
        gridPane.add(publisher, 1, 5);

        // Buttons
        Button btnCreate = new Button("Add Book");
        Button btnUpdate = new Button("Update Book");
        Button btnDelete = new Button("Delete Book");
        Button btnRefresh = new Button("Refresh Chart");

        VBox buttonBox = new VBox(10, btnCreate, btnUpdate, btnDelete, btnRefresh);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 2, 0, 1, 6);

        // PieChart Setup
        pieChart.setTitle("Book Quantities");
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);

        VBox chartBox = new VBox(10, new Label("Book Quantities Chart"), pieChart);
        chartBox.setAlignment(Pos.CENTER);

        // Main Layout
        BorderPane mainPane = new BorderPane();
        mainPane.setLeft(gridPane);
        mainPane.setCenter(chartBox);

        // CRUD Operations
        btnCreate.setOnAction(event -> {
            try {
                Book book = new Book(
                        0,
                        author.getText(),
                        title.getText(),
                        Integer.parseInt(price.getText()),
                        Integer.parseInt(quantity.getText()),
                        publisher.getText()
                );
                DBUtils.insert(book);
                refreshBookChart();
                clearFields(idField, author, title, price, quantity, publisher);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numbers for Price and Quantity.");
            }
        });

        btnUpdate.setOnAction(event -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Book book = new Book(
                        id,
                        author.getText(),
                        title.getText(),
                        Integer.parseInt(price.getText()),
                        Integer.parseInt(quantity.getText()),
                        publisher.getText()
                );
                DBUtils.update(book);
                refreshBookChart();
                clearFields(idField, author, title, price, quantity, publisher);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid ID and numbers for Price and Quantity.");
            }
        });

        btnDelete.setOnAction(event -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Confirmation");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete the last added book?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                List<Book> books = DBUtils.getAllBooks();
                if (!books.isEmpty()) {
                    DBUtils.delete(books.get(books.size() - 1).getId());
                    refreshBookChart();
                } else {
                    showAlert("Error", "No books to delete.");
                }
            }
        });

        btnRefresh.setOnAction(event -> refreshBookChart());

        // Initial Data Load
        DBUtils.connect();
        refreshBookChart();

        Scene scene = new Scene(mainPane, 800, 600);
        stage.setTitle("Bookstore Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        DBUtils.disconnect();
    }

    private void refreshBookChart() {
        pieChartData.clear();
        List<Book> books = DBUtils.getAllBooks();
        int totalQuantity = books.stream().mapToInt(Book::getQuantity).sum();

        for (Book book : books) {
            double percentage = (double) book.getQuantity() / totalQuantity * 100;
            String label = String.format("%s (%.1f%%)", book.getTitle(), percentage);
            pieChartData.add(new PieChart.Data(label, book.getQuantity()));
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
