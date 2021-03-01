package com.librarymanagment.springbootlibrary.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//Book entity
@Entity
@Table(name = "book")
public class Book {

    public Book(){
        Random random = new Random();
        this.id = Math.round(random.nextFloat() * Math.pow(10,13));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

//    public String getLibrarySection() {
//        return librarySection;
//    }
//
//    public void setLibrarySection(String librarySection) {
//        this.librarySection = librarySection;
//    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<Students> getStudentsList() {
        return studentList;
    }
    public void setStudentsList(List<Students> studentsList) {
        this.studentList = studentsList;
    }

    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ISBN")
    private Long id;
    @NotBlank
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="publisher")
    private String publisher;
//    @Column(name="librarySection")
//    private String librarySection;
    @Column(name="stock")
    private int stock;

    //mappedBy should match name of list in the Students entity,
    // in Students model, there is a list of Books called bookList
    // review fetch and cascade
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bookList")
    private List<Students> studentList = new ArrayList<>();
}
