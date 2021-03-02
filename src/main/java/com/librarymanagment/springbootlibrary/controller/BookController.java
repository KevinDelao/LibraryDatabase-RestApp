package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.BookService;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


@Controller
public class BookController {
    @Autowired
   private BookService bookService;
    @Autowired
    private StudentService studentService;

    //in charge of mapping the start up page, maps to directs to index.html
    @RequestMapping("/")
    public String viewHomePage(Model model)
    {
        List<Book> bookList = bookService.getAll();
        model.addAttribute("listBooks",bookList);
        return "`index`";
    }

    // mapping to handle creatting new students
    //mapping comes from index.html
    @RequestMapping("/newBook")
    public String enterNewBookPage(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "newBook";
    }
    //create new book from entries
    @PostMapping(value = "/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult,Model model) {
        //if any results occur with creating object then add error
        if(bindingResult.hasErrors())
        {
            model.addAttribute("inputError", true);
            return "newBook-error";
        }
        //if blank entries then show message error
        else if(book.getTitle().isBlank()||book.getAuthor().isBlank()||book.getPublisher().isBlank())
        {
            model.addAttribute("blankEntryError", true);
            return "newBook-error";
        }
        //otherwise create object
        else {
            bookService.saveBook(book);
            return "redirect:/";
        }

    }
    //used for saving for book edits,
    //did this for error messages to show properly
    @PostMapping(value = "/saveBookEdit")
    public String saveBookEdit(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult,Model model) {
        //if any results occur with creating object then add error
        if(bindingResult.hasErrors())
        {
            model.addAttribute("inputError", true);
            return "edit_book-error";
        }
        //if blank entries then show message error
        else if(book.getTitle().isBlank()||book.getAuthor().isBlank()||book.getPublisher().isBlank())
        {
            model.addAttribute("blankEntryError", true);
            return "edit_book-error";
        }
        //otherwise create object
        else {
            bookService.saveBook(book);
            return "redirect:/";
        }

    }
    //edit book using model view
    //model view will display current values in html and allow a user to edit attributes
    @RequestMapping("/edit/{id}")
    public ModelAndView editBook(@PathVariable(name = "id") Long id)
    {
        //add book attributes to show and edit
        ModelAndView mav = new ModelAndView("edit_book");
        Book book = bookService.findBook(id);
        mav.addObject("book", book);
        return mav;
    }

    //in charge of deleting book using ID
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Long ISBN, Model model)
    {
        //had to redirect as error message for index-error mapping was not showing all books
        //do not delete if book is borrowed
        // instead just prevent a delete from occuring
        if(!bookService.ifBookNotBorrowed(ISBN))
        {
            return "redirect:/";
        }
        // else delete book
        else {
            bookService.deleteBookByID(ISBN);
            return "redirect:/";
        }

    }
}
