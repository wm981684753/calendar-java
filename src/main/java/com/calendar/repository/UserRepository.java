package com.calendar.repository;

import com.calendar.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u.nickName,u.photo, c.id as c_id,c.startTime,c.endTime,c.comments " +
            "FROM User u LEFT JOIN Calendar c ON u.id = c.userId WHERE u.id = :userId AND c.isPublic=1")
    List<Object> findUserAndCalendarById(@Param("userId") Integer userId);

    List<User> findByOpenId(String openId);
}
