package org.example.java2_lika_tcholokava;

public class Book {
    private int id;
    private String author;
    private String title;
    private int price;
    private int quantity;
    private String publisher;

    // Default Constructor
    public Book() {
        this(0, "", "", 0, 0, "");
    }

    // Parameterized Constructor
    public Book(int id, String author, String title, int price, int quantity, String publisher) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.publisher = publisher;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
