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
    public DateInformation findDateInfoFromDates(Integer ISBN, Integer student_id)
    {
        return dateInformationRepo.searchBorrowDate(ISBN,student_id);
    }
}
