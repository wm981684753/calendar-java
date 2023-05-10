package com.calendar.repository;

import com.calendar.domain.Calendar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Integer> {

    Iterable<Calendar> findByUserIdOrderByEndTimeDesc(Integer userId);

    @Query("SELECT c.id,c.startTime,c.endTime,c.comments,u.nickName FROM Calendar c LEFT JOIN User u ON u.id=c.id WHERE c.isPublic=1")
    Iterable<Calendar> findAllByIsPublic();
}

