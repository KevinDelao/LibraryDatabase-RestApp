package com.librarymanagment.springbootlibrary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Students {
    //need default constructor for connection to SQL table to be made
    //Hibernate uses the default constructor to create entity objects.
    //If the default constructor is not available in any of the entities then InstantiationException occurs
    public Students(){}
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
    @Id
    @GeneratedValue
    @Column(name="student_id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private int age;
    @Column(name="department")
    private String department;


}
