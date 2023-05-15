package com.calendar.controller;

import com.calendar.domain.Calendar;
import com.calendar.interceptor.LoginRequired;
import com.calendar.service.CalendarService;
import com.calendar.vo.GetAllCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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

    @GetMapping("/allCalendar/{date}")
    @LoginRequired
    public List<Object> getAllCalendar(@PathVariable(value="date",required = true) String date) throws ParseException {
        return calendarService.getAllCalendar(date);
    }
    
    @PostMapping("updateCalendar")
    @LoginRequired
    public Boolean updateCalendar(Calendar calendar){
        return calendarService.updateCalendar(calendar);
    }

    @PostMapping("deleteCalendar")
    @LoginRequired
    public Boolean deleteCalendar(@RequestParam(value = "id",required = true) Integer id){
        return calendarService.deleteCalendar(id);
    }
}
