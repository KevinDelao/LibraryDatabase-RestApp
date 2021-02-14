package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @RequestMapping("/showStudents")
    public String getAllStudents(Model model){
        List<Book> bookList = bookService.getAll();
        model.addAttribute("listBooks",bookList);
        return "index";
    }
    @RequestMapping("/newStudent")
    public String createNewStudent(Model model)
    {
        Students students = new Students();
        model.addAttribute("student",students);
        return "new_student";
    }
    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") Students student){
        studentService.save(student);
        return "redirect:/showStudents";
    }
}
