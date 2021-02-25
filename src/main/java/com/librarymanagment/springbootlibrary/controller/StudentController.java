package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.DateInformation;
import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.DateInformationService;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute("student") Students student, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "redirect:/newStudent";
        }

        else if(student.getName().isBlank()||student.getDepartment().isBlank()||student.getEmail().isBlank()){
            return "redirect:/newStudent";
        }
        else {
            studentService.save(student);
            return "redirect:/showStudents";
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
    public String payFineRedirect(Model model)
    {
        List<Students> studentsList = studentService.getAll();
        model.addAttribute("listStudents",studentsList);
        //directs to finePayment.html
        return "finePayment";
    }
    @PostMapping(value = "/paymentFineSave")
    public String payFineProcessing(@Valid @RequestParam(name = "student_ID") String student_id,
                                    @RequestParam(name = "paymentInput") Double payment)
    {
        if(student_id.isBlank())
        {

            return "redirect:/finePayment";
        }
        //if ID contains character, reload page
        else if(!(student_id.matches("[0-9]+") && student_id.length() > 0)){
            return "redirect:/finePayment";
        }

        // if student does not exist
        else if(studentService.ifStudentExists(Integer.parseInt(student_id))==false)
        {
            return "redirect:/finePayment";
        }
        else {
            Students student = studentService.findStudent(Integer.parseInt(student_id));
            double remainder = 0;
            remainder = student.getFines() - payment;
            if (remainder < 0) {
                remainder = 0;
            }
            student.setFines(remainder);
            studentService.save(student);
            return "redirect:/showStudents";
        }
    }
}
