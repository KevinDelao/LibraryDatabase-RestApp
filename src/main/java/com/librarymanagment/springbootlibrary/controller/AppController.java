package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.resource.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {
    @Autowired
   private BookService bookService;
    @RequestMapping("/")
    public String viewHomePage(Model model){
        List<Book> bookList = bookService.getAll();
        model.addAttribute("listBooks",bookList);

        return "index";
    }

    @RequestMapping("/newBook")
    public String enterNewBookPage(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);

        return "newBook";
    }
    @RequestMapping(value = "/saveBook", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);

        return "redirect:/";
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit_book");
        Book book = bookService.findBook(id);
        mav.addObject("book", book);

        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Integer id) {
        bookService.deleteBookByID(id);
        return "redirect:/";
    }
}
