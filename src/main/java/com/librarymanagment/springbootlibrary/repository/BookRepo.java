package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Integer> {
}
