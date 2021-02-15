package com.librarymanagment.springbootlibrary.model;

import javax.persistence.*;

@Entity
@Table(name="date_info")
public class DateInformation {
    public DateInformation(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name="ISBN")
    private Integer book_id;
    @Column(name="student_id")
    private Integer student_id;
    @Column(name="borrowDate")
    private String borrowDate;
    @Column(name="dueDate")
    private String dueDate;
}
