package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.DateInformation;
import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;


    @RequestMapping("/showStudents")
    public String getAllStudents(Model model){
        List<Students> studentsList = studentService.getAll();
        model.addAttribute("listStudents",studentsList);
        return "show_students";
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
    @RequestMapping("/borrowedBooks/{id}")
    public String showBooks(@PathVariable(name = "id") Integer id,Model model, Model model2)
    {
        Students student = studentService.findStudent(id);
        List<DateInformation> dateInformations = new ArrayList<>();

        model.addAttribute("borrowedBooks",student.getBookList());
        return "borrowedBooksList";
    }
    @RequestMapping("/editStudent/{id}")
    public ModelAndView editStudent(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit_student");
        Students student = studentService.findStudent(id);
        mav.addObject("student", student);

        return mav;
    }
    @RequestMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(name = "id") Integer id) {
//        List<Book> book = studentService.findStudent(id).getBookList();
//        book.remove(studentService.findStudent(id));
        studentService.deleteStudentByID(id);

        return "redirect:/showStudents";
    }
}
