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
public class AppController {
    @Autowired
   private BookService bookService;
    @Autowired
    private StudentService studentService;
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
    @PostMapping(value = "/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "redirect:/newBook";
        }

        else if(book.getTitle().isBlank()||book.getAuthor().isBlank()||book.getPublisher().isBlank()){
            return "redirect:/newBook";
        }
        else {
            bookService.saveBook(book);
            return "redirect:/";
        }

    }
    @RequestMapping("/edit/{id}")
    public ModelAndView editBook(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_book");
        Book book = bookService.findBook(id);
        mav.addObject("book", book);

        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
//        List<Students> studentsList = bookService.findBook(id).getStudentsList();
        bookService.deleteBookByID(id);
//        for(int i=0; i <studentsList.size();i++){
//            studentService.save(studentsList.get(i));
//        }
        return "redirect:/";
    }
}
