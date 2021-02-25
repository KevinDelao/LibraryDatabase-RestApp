package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.DateInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepo extends JpaRepository<Book,Long> {
    @Query(value = "select * from book u where u.ISBN = :id1",nativeQuery = true)
    Book findBookbyISBN(@Param("id1") Long id1);

    //check if book record exists based on count
    @Query(value = "SELECT COUNT(1) FROM book WHERE isbn = :id1",nativeQuery = true)
    Integer checkIfExists(@Param("id1") Long id1);

    @Modifying
    @Transactional
    @Query(value = "delete from book u where u.ISBN = :id1",nativeQuery = true)
    void deleteBookbyISBN(@Param("id1") Long id1);


}
