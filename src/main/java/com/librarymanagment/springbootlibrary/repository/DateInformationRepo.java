package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.DateInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateInformationRepo extends JpaRepository<DateInformation,Integer> {
}
