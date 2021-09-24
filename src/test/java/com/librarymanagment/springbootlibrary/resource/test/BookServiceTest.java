package com.librarymanagment.springbootlibrary.resource.test;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.repository.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookServiceTest {
    @Autowired
    BookRepo bookRepo;
    @Test
    void getAll() {

    }

    @Test
    void findBook() {
        Book book1 = new Book();
        book1.setTitle("test");
        bookRepo.save(book1);
        Long ISBN = book1.getId();
        assertNotNull(bookRepo.findBookbyISBN(ISBN));
    }
    @Test
    void saveBook() {
    }

    @Test
    void ifBookNotExists() {
    }

    @Test
    void ifBookNotBorrowed() {
    }

    @Test
    void deleteBookByID() {
    }
}