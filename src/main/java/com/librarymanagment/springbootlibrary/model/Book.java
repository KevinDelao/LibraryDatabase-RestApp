package com.librarymanagment.springbootlibrary.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    public Book(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLibrarySection() {
        return librarySection;
    }

    public void setLibrarySection(String librarySection) {
        this.librarySection = librarySection;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

//    public List<Students> getStudentsList() {
//        return studentsList;
//    }
//
//    public void setStudentsList(List<Students> studentsList) {
//        this.studentsList = studentsList;
//    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ISBN")
    private Integer id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="publisher")
    private String publisher;
    @Column(name="librarySection")
    private String librarySection;
    @Column(name="stock")
    private int stock;

//    private List<Students> studentsList;
}
