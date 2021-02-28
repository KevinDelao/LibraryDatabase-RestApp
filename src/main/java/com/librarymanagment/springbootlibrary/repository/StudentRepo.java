package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//integer because ID of students is Integer
//Used to specify domain of entity
//this comes with built in functions like findALL
public interface StudentRepo extends JpaRepository<Students,Integer>
{
    @Query(value = "SELECT COUNT(1) FROM student_books WHERE student_id = :id1",nativeQuery = true)
    Integer checkIfBorrowingBook(@Param("id1") Integer id1);
}
