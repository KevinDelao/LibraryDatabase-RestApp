package com.librarymanagment.springbootlibrary.resource;

import com.librarymanagment.springbootlibrary.model.Students;
import com.librarymanagment.springbootlibrary.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//rest endpoint to process and retrieve data
@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepository;

    //get all students
    public List<Students> getAll(){
        return studentRepository.findAll();

    }
    //find student by ID
    public Students findStudent(Integer theId)
    {
        return studentRepository.findById(theId).get();

    }
    //chek if student is borrowing a book
    public boolean ifNotBorrowingBook(Integer id)
    {
        int count = studentRepository.checkIfBorrowingBook(id);
        //if count is zero then book not being borrowed
        if(count == 0)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public boolean ifStudentExists(Integer id){
        return studentRepository.existsById(id);
    }

    public void deleteStudentByID(Integer theId)
    {
        studentRepository.deleteById(theId);
    }

    public void save(Students student){
        studentRepository.save(student);
    }
}
