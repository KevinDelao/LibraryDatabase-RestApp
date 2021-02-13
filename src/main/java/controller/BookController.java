package controller;

import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.resource.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BookController {
    @Autowired
   private StudentService studentResource;
    @RequestMapping("/")
    public String viewHomePage(Model model){
        List<Students> studentsList = studentResource.getAll();
        model.addAttribute("listStudents",studentsList);

        return "homepage";
    }
}
