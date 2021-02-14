package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.DateInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DateInformationRepo extends JpaRepository<DateInformation,Integer> {
    //custom query to search for date info based on ID's
    @Query("select * from dateInfo u where u.ISBN = :id1 and u.student_id = :id2")
    DateInformation searchBorrowDate(@Param("id1") Integer id1, @Param("id2") Integer id2);
}
