package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.DateInformation;
import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.DateInformationService;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    DateInformationService dateInformationService;

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
    @PostMapping
    @RequestMapping(value = "/saveStudent")
    public String saveStudent(@ModelAttribute("student") Students student){
        try {
            studentService.save(student);
            return "redirect:/showStudents";
        } catch (Exception ex) {
            return "redirect:/showStudents";
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
        }

    }

    @RequestMapping("/borrowedBooks/{id}")
    public String showBooks(@PathVariable(name = "id") Integer id,Model model, Model model2)
    {
        Students student = studentService.findStudent(id);
        List <Book> bookList = student.getBookList();
        List<DateInformation> dateInformations = new ArrayList<>();
        for(int i = 0; i< bookList.size();i++){
            // find date info based on book ID and student id and the add date info object to list
            dateInformations.add(dateInformationService.findDateInfoFromDates(bookList.get(i).getId(),student.getId()));
        }
        model.addAttribute("borrowedBooks",bookList);
        model2.addAttribute("dateInfoLists",dateInformations);
        return "borrowedBooksList";
    }
    @RequestMapping("/editStudent/{id}")
    public ModelAndView editStudent(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit_student");
        Students student = studentService.findStudent(id);
        //

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
    @RequestMapping("/finePayment")
    public String payFineRedirect() {

        //directs to finePayment.html
        return "finePayment";
    }
    @PostMapping
    @RequestMapping(value = "/paymentFineSave")
    public String payFineProcessing(@RequestParam(name = "student_ID") String student_id,
                                    @RequestParam(name = "payment") Double payment) {

        Students student = studentService.findStudent(Integer.parseInt(student_id));
        double remainder = 0;
        remainder = student.getFines() - payment;
        if(remainder < 0)
        {
            remainder = 0;
        }
        student.setFines(remainder);
        studentService.save(student);
        return "redirect:/showStudents";
    }
}
