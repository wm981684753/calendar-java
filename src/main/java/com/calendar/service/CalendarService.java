package com.calendar.service;

import com.calendar.domain.Calendar;
import com.calendar.repository.CalendarRepository;
import com.calendar.utils.CastEntityUtil;
import com.calendar.utils.Helper;
import com.calendar.vo.GetAllCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalendarService extends BaseService{

    @Autowired
    private CalendarRepository calendarRepository;

    /**
     * @note 创建新的日程
     * @param calendar calendar
     * @return Bool
     * @date 2023/05/10
     * @auther weiming
     */
    public Boolean createCalendar(Calendar calendar){
        Integer user_id = this.getUserInfo().getId();

        //TODO 验证当前用户&当前时间点是否已经有安排

        calendar.setUserId(user_id);
        if(calendar.getIsPublic()==null){
            calendar.setIsPublic(1);
        }
        Calendar result = calendarRepository.save(calendar);
        if(result.getId()!=null){
            return true;
        }
        return false;
    }

    /**
     * @note 创建新的日程
     * @return List<Object>
     * @date 2023/05/10
     * @auther weiming
     */
    public List<Object> getMyCalendar(){
        Integer user_id = this.getUserInfo().getId();
        Iterable<Calendar> calendarInfo = calendarRepository.findByUserIdOrderByEndTimeDesc(user_id);
        long nowTime = new Date().getTime()/1000;//获取当前时间戳
        List<Object> myCalendar = new ArrayList<>();
        for (Calendar calendar : calendarInfo) {
            Map<String,Object> calendarItem = new HashMap<>();
            calendarItem.put("status",nowTime<=calendar.getEndTime()?1:0);
            calendarItem.put("startTime", Helper.stampToDate(calendar.getStartTime(), "Y.M.d"));
            calendarItem.put("end_time", Helper.stampToDate(calendar.getEndTime(), "Y.M.d"));
            calendarItem.put("comments", calendar.getComments());
            calendarItem.put("isPublic", calendar.getIsPublic());
            calendarItem.put("id", calendar.getId());
            myCalendar.add(calendarItem);
        }
        return myCalendar;
    }

    public List<GetAllCalendar> getAllCalendar(){
        List<Object[]> findAllByIsPublic = calendarRepository.findAllByIsPublic();
        try {
            List<GetAllCalendar> getAllCalendars = CastEntityUtil.castEntity(findAllByIsPublic, GetAllCalendar.class);
            return getAllCalendars;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
