package com.librarymanagment.springbootlibrary.model;

import javax.persistence.*;

@Table(name = "admin")
@Entity
public class Admin {
    public Admin(){}
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="admin_id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
}
