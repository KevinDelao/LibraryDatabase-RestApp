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

// Controller for actions related to student object
@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    DateInformationService dateInformationService;

    //Gets all students to display
    @RequestMapping("/showStudents")
    public String getAllStudents(Model model){
        List<Students> studentsList = studentService.getAll();
        model.addAttribute("listStudents",studentsList);
        return "show_students";
    }
    //creates student object and maps to new_student.html
    @RequestMapping("/newStudent")
    public String createNewStudent(Model model)
    {
        Students students = new Students();
        model.addAttribute("student",students);
        return "new_student";
    }
    //create new students
    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute("student") Students student, BindingResult bindingResult, Model model){

        //if any errors with form occurs bindingResult will catch it and then add error using model
        if(bindingResult.hasErrors()){
            model.addAttribute("inputError", true);
            return "new_student-error";        }

        //if any input is blank then add error message
        else if(student.getName().isBlank()||student.getDepartment().isBlank()||student.getEmail().isBlank()){
            model.addAttribute("blankEntryError", true);
            return "new_student-error";
        }
        //otherwise save student
        else {
            studentService.save(student);
            return "redirect:/showStudents";
        }

    }
    //save edits made
    @PostMapping("/saveStudentEdit")
    public String saveStudentEdit(@Valid @ModelAttribute("student") Students student, BindingResult bindingResult,Model model)
    {
        //if any input errors, show message
        if(bindingResult.hasErrors()){
            model.addAttribute("inputError", true);
            return "edit_student-error";
        }
        //if inputs are blank, show message
        else if(student.getName().isBlank()||student.getDepartment().isBlank()||student.getEmail().isBlank()){
            model.addAttribute("blankEntryError", true);
            return "edit_student-error";
        }
        //otherwise save edits
        else {
            studentService.save(student);
            return "redirect:/showStudents";
        }

    }
    //get borrowed books and show them
    @RequestMapping("/borrowedBooks/{id}")
    public String showBooks(@PathVariable(name = "id") Integer id,Model model, Model model2)
    {
        //find student by id
        Students student = studentService.findStudent(id);
        //get list of books borrowed by student
        List <Book> bookList = student.getBookList();
        List<DateInformation> dateInformations = new ArrayList<>();
        //loop will find dates for every book borrowed by student
        for(int i = 0; i< bookList.size();i++){
            // find date info based on book ID and student id and the add date info object to list
            dateInformations.add(dateInformationService.findDateInfoFromDates(bookList.get(i).getId(),student.getId()));
        }
        model.addAttribute("borrowedBooks",bookList);
        //dates will be showed in separate table as thymeleaf does not seem to allow looping for two objects
        model2.addAttribute("dateInfoLists",dateInformations);
        return "borrowedBooksList";
    }
    //edit student with model and view
    @RequestMapping("/editStudent/{id}")
    public ModelAndView editStudent(@PathVariable(name = "id") Integer id) {
        //edit_student.html will be used to view student object for editing
        ModelAndView mav = new ModelAndView("edit_student");
        Students student = studentService.findStudent(id);
        mav.addObject("student", student);
        //will redirect to edit_student
        return mav;
    }

    @RequestMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(name = "id") Integer id)
    {
        //if student is borrowing a book then cannot delete student
        //did not add message because creating index-error.html did show books and error messages
        if(!studentService.ifNotBorrowingBook(id))
        {
            return "redirect:/showStudents";
        }
        // cannot delete student if debt is owed
        else if (studentService.findStudent(id).getFines()>0)
        {
            return "redirect:/showStudents";
        }
        //otherwise delete student
        else {
            studentService.deleteStudentByID(id);
            return "redirect:/showStudents";
        }
    }
//    @RequestMapping(value={"/finePayment", "/finePayment-error"})
    //handles fee payment
    @RequestMapping("/finePayment")
    public String payFineRedirect(Model model)
    {
        //this list of students is to show all the students and if each student
        //owes a fine or not
        List<Students> studentsList = studentService.getAll();
        model.addAttribute("listStudents",studentsList);
        //directs to finePayment.html
        return "finePayment";
    }

    //did not add custom error html page because student list was not loading.
    //this function handles fee payments
    @PostMapping(value = "/paymentFineSave")
    public String payFineProcessing(@Valid @RequestParam(name = "student_ID") String student_id,
                                    @RequestParam(name = "paymentInput") Double payment)
    {
        //if blank reload page
        if(student_id.isBlank())
        {
            return "redirect:/finePayment";
        }
        //if ID contains character, reload page
        else if(!(student_id.matches("[0-9]+") && student_id.length() > 0)){
            return "redirect:/finePayment";
        }
        // if student does not exist reload page
        else if(!studentService.ifStudentExists(Integer.parseInt(student_id)))
        {
            return "redirect:/finePayment";
        }
        //otherwise calculate fee payment
        else {
            Students student = studentService.findStudent(Integer.parseInt(student_id));
            double remainder = 0;
            remainder = student.getFines() - payment;
            //if payment is larger than amount due, then set fine equal to zero
            if (remainder < 0) {
                remainder = 0;
            }
            student.setFines(remainder);
            studentService.save(student);
            return "redirect:/showStudents";
        }
    }
}
