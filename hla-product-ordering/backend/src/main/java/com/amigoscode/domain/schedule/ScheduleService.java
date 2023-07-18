package com.amigoscode.domain.schedule;

import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionNotFoundException;
import com.amigoscode.domain.version.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final VersionService versionService;


    public Schedule findById(Integer id){

        Optional<Schedule> schedule = scheduleRepository.findById(id);
        Version version =  getLatestVersion(id);
        schedule.ifPresent(value -> value.setVersion(version));
        return scheduleRepository.findById(id).
                orElseThrow(ScheduleNotFoundException::new);
    }
    public PageSchedule findAll(Pageable pageable) {
        PageSchedule pageSchedule = scheduleRepository.findAll(pageable);
        pageSchedule.getSchedules().forEach(schedule -> {
            Version version =  getLatestVersion(schedule.getId());
            schedule.setVersion(version);
        });
        return pageSchedule;
    }

    public Schedule save(Schedule scheduleToSave) {
        Schedule schedule = scheduleRepository.save(scheduleToSave);
        Version version = versionService.save(scheduleToSave.getVersion());
        schedule.setVersion(version);
        return schedule;
    }

    public void update(Schedule schedule){
        scheduleRepository.update(schedule);
        versionService.update(schedule.getVersion());
    }

    public void removeById(Integer id){
        scheduleRepository.removeById(id);
        versionService.removeByScheduleId(id);
    }

    private Version getLatestVersion(Integer id) {
        return versionService.findAllVersionsByScheduleId(id)
                .stream()
                .max(Comparator.comparing(Version::getVersion))
                .orElseThrow(VersionNotFoundException::new);
    }

}
