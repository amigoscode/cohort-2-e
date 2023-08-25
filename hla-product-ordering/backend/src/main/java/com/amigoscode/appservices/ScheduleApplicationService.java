package com.amigoscode.appservices;

import com.amigoscode.domain.schedule.PageSchedule;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleApplicationService {

    private final ScheduleService scheduleService;
    private final IAuthenticationFacade authenticationFacade;

    @Transactional
    public Schedule findById(Integer id) {
        return scheduleService.findById(id);
    }

    @Transactional
    public PageSchedule findAll(Pageable pageable) {
        return scheduleService.findAll(pageable);
    }

    @Transactional
    public Schedule save(Schedule scheduleToSave) {
        return scheduleService.save(scheduleToSave, authenticationFacade.getLoggedInUserId());
    }

    @Transactional
    public void update(Schedule schedule) {
        scheduleService.update(schedule, authenticationFacade.getLoggedInUserId());
    }

    @Transactional
    public void removeById(Integer id) {
        scheduleService.removeById(id);
    }
}

