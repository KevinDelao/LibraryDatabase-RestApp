package com.librarymanagment.springbootlibrary.resource;

import com.librarymanagment.springbootlibrary.model.DateInformation;
import com.librarymanagment.springbootlibrary.repository.DateInformationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateInformationService {
    @Autowired
    DateInformationRepo dateInformationRepo;
    public void saveDateInfo(DateInformation dateInformation){
        dateInformationRepo.save(dateInformation);
    }
    //uses custom query search to look up date by ISBN and student ID
    public DateInformation findDateInfoFromDates(Long ISBN, Integer student_id)
    {
        return dateInformationRepo.searchBorrowDate(ISBN,student_id);
    }
    //delete dates by ISBN and student ID
    public void deleteDates(Long ISBN, Integer student_id)
    {
         dateInformationRepo.deleteDates(ISBN,student_id);
    }

}
