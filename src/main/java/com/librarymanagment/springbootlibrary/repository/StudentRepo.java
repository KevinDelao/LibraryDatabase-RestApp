package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
//integer because ID of students is Integer
//Used to specify domain of entity
//this comes with built in functions like findALL
public interface StudentRepo extends JpaRepository<Students,Integer>{
}
