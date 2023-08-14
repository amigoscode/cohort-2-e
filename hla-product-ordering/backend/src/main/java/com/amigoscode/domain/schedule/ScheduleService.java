package com.amigoscode.domain.schedule;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.note.NoteService;
import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionNotFoundException;
import com.amigoscode.domain.version.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final VersionService versionService;
    private final NoteService noteService;

    public Schedule findById(Integer id){

        Optional<Schedule> schedule = scheduleRepository.findById(id);

        schedule.ifPresent(value -> {
            Version version =  getLatestVersion(id);
            Note note = noteService.findByScheduleIdAndVersion(id, version.getVersion());
            value.setVersion(version);
            value.setNote(note);
        });

        return schedule.orElseThrow(ScheduleNotFoundException::new);
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

    public Schedule save(Schedule scheduleToSave, Integer userId) {
        if (scheduleToSave.getId() != null && scheduleRepository.findById(scheduleToSave.getId()).isPresent()) {
            throw new ScheduleAlreadyExistsException();
        }
        ZonedDateTime createdAt = ZonedDateTime.now(ZoneOffset.UTC);
        Schedule schedule = scheduleRepository.save(scheduleToSave);
        Version versionToSave = getVersion(scheduleToSave, createdAt, schedule, userId);
        Version version = versionService.save(versionToSave);
        Note noteToSave = getNote(scheduleToSave, createdAt, schedule, version, userId);
        Note note = noteService.save(noteToSave);
        schedule.setVersion(version);
        schedule.setNote(note);
        return schedule;
    }

    private Note getNote(Schedule scheduleToSave, ZonedDateTime createdAt, Schedule schedule, Version version, Integer createdBy) {
        Note noteToSave = scheduleToSave.getNote();
        noteToSave.setScheduleId(schedule.getId());
        noteToSave.setScheduleVersion(version.getVersion());
        noteToSave.setCreatedAt(createdAt);
        noteToSave.setCreatedBy(createdBy);
        return noteToSave;
    }

    private Version getVersion(Schedule scheduleToSave, ZonedDateTime createdAt, Schedule schedule, Integer createdBy) {
        Version versionToSave = scheduleToSave.getVersion();
        versionToSave.setScheduleId(schedule.getId());
        versionToSave.setUpdatedAt(createdAt);
        versionToSave.setUpdatedBy(createdBy);
        return versionToSave;
    }

    public void update(Schedule schedule){
        if (schedule.getId() == null || scheduleRepository.findById(schedule.getId()).isEmpty()) {
            throw new ScheduleNotFoundException();
        }
        scheduleRepository.update(schedule);
        versionService.update(schedule.getVersion());
        noteService.update(schedule.getNote());
    }

    public void removeById(Integer id){
        noteService.removeByScheduleId(id);
        versionService.removeByScheduleId(id);
        scheduleRepository.removeById(id);
    }

    public Version getLatestVersion(Integer id) {
        return versionService.findAllVersionsByScheduleId(id)
                .stream()
                .max(Comparator.comparing(Version::getVersion))
                .orElseThrow(VersionNotFoundException::new);
    }

}
