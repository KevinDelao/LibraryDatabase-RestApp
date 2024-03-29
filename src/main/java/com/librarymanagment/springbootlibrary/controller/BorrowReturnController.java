package com.librarymanagment.springbootlibrary.controller;

import com.librarymanagment.springbootlibrary.model.Book;
import com.librarymanagment.springbootlibrary.model.DateInformation;
import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.BookService;
import com.librarymanagment.springbootlibrary.resource.DateInformationService;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BorrowReturnController {
    @Autowired
    BookService bookService;
    @Autowired
    StudentService studentService;
    @Autowired
    DateInformationService dateInformationService;

    // handles the borrow book link that comes from index.html
    //redirects to borrowBook.html
    @RequestMapping("/borrowBook")
    public String goToBorrowPage() {

        return "borrowBook";
    }

    //handles saving book variable inputs
    @PostMapping(value = "/borrowBookSave")
    public String borrowBook(@RequestParam(name = "book_ISBN") String ISBN,
                             @RequestParam(name = "student_ID") String SID,
                             @RequestParam(name = "borrowDate") String borrowDate,
                             @RequestParam(name = "dueDate") String dueDate, Model model) {

        //if ISBN or SID(Student ID) is blank, add error
        if (ISBN.isBlank() || SID.isBlank()) {
            model.addAttribute("invalidEntryError", true);
            return "borrowBook-error";
        }
        // if String ISBN contains a character, add error
        else if (!(ISBN.matches("[0-9]+") && ISBN.length() > 0)) {
            model.addAttribute("invalidSIDError", true);
            return "borrowBook-error";
        }
        // if String SID contains a character, add error
        else if (!(SID.matches("[0-9]+") && SID.length() > 0)) {
            model.addAttribute("invalidISBNError", true);
            return "borrowBook-error";
        }
        // if student does not exist
        // does not handle large input like long ISBN, NEED TO FIX
        else if (!studentService.ifStudentExists(Integer.parseInt(SID))) {
            model.addAttribute("noStudentFoundError", true);
            return "borrowBook-error";
        }
        // if book does not exist, add error
        else if (bookService.ifBookNotExists(Long.parseLong(ISBN))) {
            model.addAttribute("noBookFoundError", true);
            return "borrowBook-error";

        }
        //if out of stock, add error
        else if (bookService.findBook(Long.parseLong(ISBN)).getStock() == 0) {
            model.addAttribute("outOfStockError", true);
            return "borrowBook-error";
        }
        // if book already borrowed, add error
        else if (studentService.findStudent(Integer.parseInt(SID)).getBookList().contains(bookService.findBook(Long.parseLong(ISBN)))) {
            model.addAttribute("alreadyBorrowedError", true);
            return "borrowBook-error";
        }
        //if dates borrow and due date same
        else if(borrowDate.compareTo(dueDate) == 0){
            model.addAttribute("dateEqualError", true);
            return "borrowBook-error";
        }
        //if fines are due then cannot borrow book, add error
        else if(studentService.findStudent(Integer.parseInt(SID)).getFines() > 0)
        {
            model.addAttribute("finesDueError", true);
            return "borrowBook-error";
        }
        //otherwise borrow book
        else {
            // find book and student by ID
            Students student = studentService.findStudent(Integer.parseInt(SID));
            Book book = bookService.findBook(Long.parseLong(ISBN));
            // get students list of books and add new book
            student.getBookList().add(book);
            // need to add student to books student list for many to many association
            book.getStudentsList().add(student);
            //reduce stock
            book.setStock(book.getStock() - 1);
            // create new date object
            DateInformation dateInformation = new DateInformation();
            //set borrow date, due, ISBN, SID for query searching
            dateInformation.setBook_id(Long.parseLong(ISBN));
            dateInformation.setStudent_id(Integer.parseInt(SID));
            dateInformation.setBorrowDate(borrowDate);
            dateInformation.setDueDate(dueDate);
            //save objects
            dateInformationService.saveDateInfo(dateInformation);
            studentService.save(student);
            bookService.saveBook(book);
            return "redirect:/";
        }
    }
    //used to handle returnBook link and maps to returnBook.html
    @RequestMapping("/returnBook")
    public String bookReturnMapping() {
        return "returnBook";

    }
    //handles saving book form in returnBook.html
    @PostMapping(value = "/returnBookSave")
    public String bookReturnProcessing(@RequestParam(name = "book_ISBN") String ISBN,
                                       @RequestParam(name = "student_ID") String SID,
                                       @RequestParam(name = "returnDate") String returnDate, Model model) {

        //if ID entries are blank
        if (ISBN.isBlank() || SID.isBlank()) {
            model.addAttribute("invalidEntryError", true);
            return "returnBook-error";

        }
        // if String ISBN contains a character
        else if (!(ISBN.matches("[0-9]+") && ISBN.length() > 0))
        {
            model.addAttribute("invalidISBNError", true);
            return "returnBook-error";
        }
        // if String SID contains a character
        else if (!(SID.matches("[0-9]+") && SID.length() > 0))
        {
            model.addAttribute("invalidSIDError", true);
            return "returnBook-error";
        }
        // if student does not exist
        else if (!studentService.ifStudentExists(Integer.parseInt(SID))) {
            model.addAttribute("noStudentFoundError", true);
            return "returnBook-error";
        }
        // if book does not exist
        else if (bookService.ifBookNotExists(Long.parseLong(ISBN))) {
            model.addAttribute("noBookFoundError", true);
            return "returnBook-error";

        }
        // if no dates found
        else if(dateInformationService.findDateInfoFromDates(Long.parseLong(ISBN),
                Integer.parseInt(SID)) == null)
        {
            model.addAttribute("noDatesFoundError", true);
            return "returnBook-error";
        }
        //if no errors return book
        else {
            //find book and student by ID
            Book book = bookService.findBook(Long.parseLong(ISBN));
            Students student = studentService.findStudent(Integer.parseInt(SID));
            // get date of book due, using ISBN and SID
            DateInformation dateInformation = dateInformationService.findDateInfoFromDates(Long.parseLong(ISBN),
                    Integer.parseInt(SID));

            double fines = 0;
            //if book is overdue, then add a fine
            if (returnDate.compareTo(dateInformation.getDueDate()) > 0) {
                //I made the fine 5 dollars just to match the fines at my local library
                fines = fines + 5.0;
            }
            student.setFines(student.getFines() + fines);
            student.getBookList().remove(book);
            book.getStudentsList().remove(student);
            //book returned so increase stock by 1
            book.setStock(book.getStock() + 1);
            // save objects
            dateInformationService.deleteDates(book.getId(), student.getId());
            studentService.save(student);
            bookService.saveBook(book);
            // back to homepage
            return "redirect:/";
        }

    }

}
