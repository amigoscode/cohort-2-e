package com.amigoscode.domain.schedule;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.note.NoteService;
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
    private final NoteService noteService;


    public Schedule findById(Integer id){

        Optional<Schedule> schedule = scheduleRepository.findById(id);
        Version version =  getLatestVersion(id);
        Note note = noteService.findByScheduleIdAndVersion(id, version.getVersion());
        schedule.ifPresent(value -> {
            value.setVersion(version);
            value.setNote(note);
        });

        return scheduleRepository.findById(id).
                orElseThrow(ScheduleNotFoundException::new);
    }
    public PageSchedule findAll(Pageable pageable) {
        PageSchedule pageSchedule = scheduleRepository.findAll(pageable);
        pageSchedule.getSchedules().forEach(schedule -> {
            Version version = getLatestVersion(schedule.getId());
            schedule.setVersion(version);
            Note note = noteService.findByScheduleIdAndVersion(schedule.getId(), version.getVersion());
            schedule.setNote(note);
        });
        return pageSchedule;
    }

    public Schedule save(Schedule scheduleToSave) {
        Schedule schedule = scheduleRepository.save(scheduleToSave);
        Version version = versionService.save(scheduleToSave.getVersion());
        Note note = noteService.save(scheduleToSave.getNote());
        schedule.setVersion(version);
        schedule.setNote(note);
        return schedule;
    }

    public void update(Schedule schedule){
        scheduleRepository.update(schedule);
        versionService.update(schedule.getVersion());
        noteService.update(schedule.getNote());
    }

    public void removeById(Integer id){
        scheduleRepository.removeById(id);
        versionService.removeByScheduleId(id);
        noteService.removeByScheduleId(id);
    }

    private Version getLatestVersion(Integer id) {
        return versionService.findAllVersionsByScheduleId(id)
                .stream()
                .max(Comparator.comparing(Version::getVersion))
                .orElseThrow(VersionNotFoundException::new);
    }

}
