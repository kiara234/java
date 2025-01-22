package org.example.java_lika_tcholokava;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
}
