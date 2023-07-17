package com.amigoscode.domain.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;


    public Schedule findById(Integer id){
        return scheduleRepository.findById(id).
                orElseThrow(ScheduleNotFoundException::new);
    }

    public PageSchedule findAll(Pageable pageable) { return scheduleRepository.findAll(pageable); }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void update(Schedule schedule){
        scheduleRepository.update(schedule);
    }

    public void removeById(Integer id){
        scheduleRepository.removeById(id);
    }

}
