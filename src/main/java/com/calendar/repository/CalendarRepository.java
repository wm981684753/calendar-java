package com.calendar.repository;

import com.calendar.domain.Calendar;
import com.calendar.vo.GetAllCalendar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Integer> {

    Iterable<Calendar> findByUserIdOrderByEndTimeDesc(Integer userId);

    @Query(value = "SELECT c.id, c.start_time, c.end_time, c.comments,u.nick_name,(case when UNIX_TIMESTAMP()>c.end_time then 0 else 1 end) as status FROM calendar c,user u where c.user_id=u.id and c.public=1 order by c.end_time desc",nativeQuery = true)
    List<Object[]> findAllByIsPublic();
}

