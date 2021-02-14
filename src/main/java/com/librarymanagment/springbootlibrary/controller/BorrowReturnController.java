package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.DateInformation;
import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.BookService;
import com.librarymanagment.springbootlibrary.resource.DateInformationService;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BorrowReturnController {
    @Autowired
    BookService bookService;
    @Autowired
    StudentService studentService;
    @Autowired
    DateInformationService dateInformationService;
    @RequestMapping("/borrowBook")
    public String goToBorrowPage(){
        return "borrowBook";
    }
    @RequestMapping(value = "/borrowBookSave", method = RequestMethod.POST)
    public String borrowBook(@RequestParam(name = "book_ISBN") String ISBN,
                             @RequestParam(name = "student_ID") String SID,
                             @RequestParam(name = "borrowDate") String borrowDate,
                             @RequestParam(name = "dueDate") String dueDate)
    {
        Students student = studentService.findStudent(Integer.parseInt(SID));
        Book book = bookService.findBook(Integer.parseInt(ISBN));
        student.getBookList().add(book);
        book.getStudentsList().add(student);
        DateInformation dateInformation = new DateInformation();
        dateInformation.setBook_id(Integer.parseInt(ISBN));
        dateInformation.setStudent_id(Integer.parseInt(SID));
        dateInformation.setBorrowDate(borrowDate);
        dateInformation.setDueDate(dueDate);
        dateInformationService.saveDateInfo(dateInformation);
        studentService.save(student);
        bookService.saveBook(book);
        return "redirect:/";
    }
}
