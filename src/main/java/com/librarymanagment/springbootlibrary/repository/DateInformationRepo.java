package com.librarymanagment.springbootlibrary.repository;

import com.librarymanagment.springbootlibrary.model.DateInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DateInformationRepo extends JpaRepository<DateInformation,Integer> {
    //custom query to search for date info based on ID's
    //this function will return a DateInformation entity that it finds that matches the search
    @Query(value = "select * from date_info u where u.ISBN = :id1 and u.student_id = :id2",nativeQuery = true)
    DateInformation searchBorrowDate(@Param("id1") Integer id1, @Param("id2") Integer id2);

    //need both Modifying and Transactional for delete operation to work
    @Modifying
    @Transactional
    @Query(value = "Delete from date_info u where u.ISBN = :id1 and u.student_id = :id2",nativeQuery = true)
    void deleteDates(@Param("id1") Integer id1, @Param("id2") Integer id2);
}
