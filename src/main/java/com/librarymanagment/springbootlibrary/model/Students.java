package com.librarymanagment.springbootlibrary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="students")
public class Students {
    //need default constructor for connection to SQL table to be made
    //Hibernate uses the default constructor to create entity objects.
    //If the default constructor is not available in any of the entities then InstantiationException occurs
    public Students(){
        this.fines = 0;
    }
    public Students(String name,int age,String department)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getFines() {
        return fines;
    }

    public void setFines(int fines) {
        this.fines = fines;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="student_id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private int age;
    @Column(name="department")
    private String department;
    @Column(name="email")
    private String email;
    @Column(name="fines")
    private int fines;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_books",
            joinColumns = { @JoinColumn(name = "student_id")},
            inverseJoinColumns = { @JoinColumn (name = "ISBN")})
    private List<Book> bookList = new ArrayList<>();


}
