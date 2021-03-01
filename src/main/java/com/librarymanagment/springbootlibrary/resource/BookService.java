package com.librarymanagment.springbootlibrary.resource;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepo bookRepo;

    //get all students, find all returns book list
    public List<Book> getAll(){
        return bookRepo.findAll();

    }
    //find book by ID
    public Book findBook(Long theId)
    {
        return bookRepo.findBookbyISBN(theId);
    }
    //save book to database
    public void saveBook(Book book)
    {
        bookRepo.save(book);
    }
    //search that uses custom query to check if book exists
    public boolean ifBookNotExists(Long id)
    {
       int count = bookRepo.checkIfExists(id);
       //if count is zero, then book does not exist
       if(count == 0)
       {
           return true;
       }
       else{
           return false;
       }
    }
    //checks if book is borrowed
    public boolean ifBookNotBorrowed(Long id)
    {
        int count = bookRepo.checkIfBorrowed(id);
        if(count == 0)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public void deleteBookByID(Long theId)
    {
        bookRepo.deleteBookbyISBN(theId);
    }
}
