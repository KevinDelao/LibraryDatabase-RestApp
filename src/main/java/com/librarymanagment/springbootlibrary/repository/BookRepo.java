package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.DateInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepo extends JpaRepository<Book,Long> {
    // Custom query to find book by ID
    //needed custom query because using Long ID for ISBN  is too large for built in findByID function
    @Query(value = "select * from book u where u.ISBN = :id1",nativeQuery = true)
    Book findBookbyISBN(@Param("id1") Long id1);

    //check if book record exists based on count, if count is greater than zero then
    //means book exists
    @Query(value = "SELECT COUNT(1) FROM book WHERE isbn = :id1",nativeQuery = true)
    Integer checkIfExists(@Param("id1") Long id1);

    //used to check if book is borrowed someone by looking at many to many table
    //will be used to prevent a deletion of a borrowed book
    @Query(value = "SELECT COUNT(1) FROM student_books WHERE isbn = :id1",nativeQuery = true)
    Integer checkIfBorrowed(@Param("id1") Long id1);

    // deletes book by ID, needed custom query because ISBN is too large for built in function
    //needed Modifying and Transactional to work
    @Modifying
    @Transactional
    @Query(value = "delete from book u where u.ISBN = :id1",nativeQuery = true)
    void deleteBookbyISBN(@Param("id1") Long id1);


}
