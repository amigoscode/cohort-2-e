package com.amigoscode.domain.schedule;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.note.NoteService;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionNotFoundException;
import com.amigoscode.domain.version.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final VersionService versionService;
    private final NoteService noteService;
    private final PatientService patientService;

    public Schedule findById(Integer id) {

        Optional<Schedule> schedule = scheduleRepository.findById(id);

        schedule.ifPresent(value -> {
            Version version = getLatestVersion(id);
            Note note = noteService.findByScheduleIdAndVersion(id, version.getVersion());
            Patient patient = patientService.findById(value.getPatientId());
            value.setPatient(patient);
            value.setVersion(version);
            value.setNote(note);
        });

        return schedule.orElseThrow(ScheduleNotFoundException::new);
    }

    public PageSchedule findAll(Pageable pageable) {
        PageSchedule pageSchedule = scheduleRepository.findAll(pageable);
        pageSchedule.getSchedules().forEach(schedule -> {
            if (!versionService.findAllVersionsByScheduleId(schedule.getId()).isEmpty()) {
                Version version = getLatestVersion(schedule.getId());
                schedule.setVersion(version);
                Optional.ofNullable(noteService.findByScheduleIdAndVersion(schedule.getId(), version.getVersion()))
                        .ifPresent(schedule::setNote);
            }
            Patient patient = patientService.findById(schedule.getPatientId());
            schedule.setPatient(patient);
        });
        return pageSchedule;
    }

    public Schedule save(Schedule scheduleToSave, Integer userId) {
        if (scheduleToSave.getId() != null && scheduleRepository.findById(scheduleToSave.getId()).isPresent()) {
            throw new ScheduleAlreadyExistsException();
        }
        ZonedDateTime createdAt = ZonedDateTime.now(ZoneOffset.UTC);
        Schedule schedule = scheduleRepository.save(scheduleToSave);
        if (schedule.getVersion() != null) {
            Version versionToSave = getVersion(scheduleToSave, createdAt, schedule, userId);
            Version version = versionService.save(versionToSave);
            Note noteToSave = getNote(scheduleToSave.getNote(), createdAt, schedule.getId(), version.getVersion(), userId);
            Note note = noteService.save(noteToSave);
            schedule.setVersion(version);
            schedule.setNote(note);
        }
        return schedule;
    }

    private Note getNote(Note noteToSave, ZonedDateTime createdAt, Integer scheduleId, Integer version, Integer createdBy) {
        noteToSave.setScheduleId(scheduleId);
        noteToSave.setScheduleVersion(version);
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

    public void update(Schedule schedule, Integer userId) {
        ZonedDateTime createdAt = ZonedDateTime.now(ZoneOffset.UTC);
        if (schedule.getId() == null || scheduleRepository.findById(schedule.getId()).isEmpty()) {
            throw new ScheduleNotFoundException();
        }
        scheduleRepository.update(schedule);
        if (versionService.findAllVersionsByScheduleId(schedule.getId()).isEmpty()) {
            Version versionToSave = getVersion(schedule, createdAt, schedule, userId);
            Version version = versionService.save(versionToSave);
            Note noteToSave = getNote(schedule.getNote(), createdAt, schedule.getId(), version.getVersion(), userId);
            Note note = noteService.save(noteToSave);

        } else {
            versionService.update(schedule.getVersion());
            noteService.update(schedule.getNote());
        }
    }

    public void removeById(Integer id) {
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
