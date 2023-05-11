package com.calendar.controller;

import com.calendar.domain.Calendar;
import com.calendar.interceptor.LoginRequired;
import com.calendar.service.CalendarService;
import com.calendar.vo.GetAllCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    /**
     * @note 创建新的日程
     * @param calendar calendar
     * @return Boolean
     */
    @PostMapping("/createCalendar")
    @LoginRequired
    public Boolean createCalendar(Calendar calendar){
        return calendarService.createCalendar(calendar);
    }

    /**
     * @note 获取当前用户的全部日程
     * @return List<Object>
     */
    @GetMapping("/myCalendar")
    @LoginRequired
    public List<Object> getMyCalender() {
        return calendarService.getMyCalendar();
    }

    @GetMapping("/allCalendar")
    @LoginRequired
    public List<GetAllCalendar> getAllCalendar(){
        return calendarService.getAllCalendar();
    }
}
