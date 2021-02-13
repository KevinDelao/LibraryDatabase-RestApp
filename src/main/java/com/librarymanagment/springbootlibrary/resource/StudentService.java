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

    public List<Students> getAll(){
        return studentRepository.findAll();

    }

    public Students findStudent(Integer theId)
    {
        return studentRepository.findById(theId).get();

    }
    //used to push data to students table
    //saves a student entered through the students/load pathway POST method

    public List<Students> persist(final Students student){
        studentRepository.save(student);
        return studentRepository.findAll();
    }
    public List<Students> deleteCustomer(Integer theId)
    {
        studentRepository.deleteById(theId);
        return studentRepository.findAll();
    }
}
