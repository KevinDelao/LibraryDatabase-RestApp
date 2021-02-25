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

    public List<Book> getAll(){
        return bookRepo.findAll();

    }

    public Book findBook(Long theId)
    {
        return bookRepo.findBookbyISBN(theId);
    }
    public void saveBook(Book book)
    {
        bookRepo.save(book);
    }
    public boolean ifBookNotExists(Long id){
       int count = bookRepo.checkIfExists(id);
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
