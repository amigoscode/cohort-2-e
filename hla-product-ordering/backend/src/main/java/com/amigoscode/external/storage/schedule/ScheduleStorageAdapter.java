package com.amigoscode.external.storage.schedule;

import com.amigoscode.domain.schedule.PageSchedule;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleAlreadyExistsException;
import com.amigoscode.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class ScheduleStorageAdapter implements ScheduleRepository{
    private final JpaScheduleRepository scheduleRepository;

    private final ScheduleEntityMapper mapper;


    @Override
    public Optional<Schedule> findById(final Integer id) {
        return scheduleRepository.findById(id).map(mapper::toDomain);
    }


    @Override
    public PageSchedule findAll(Pageable pageable) {
        Page<ScheduleEntity> pageOfSchedulesEntity = scheduleRepository.findAll(pageable);
        List<Schedule> schedulesOnCurrentPage = pageOfSchedulesEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageSchedule(
            schedulesOnCurrentPage,
            pageable.getPageNumber() +1,
            pageOfSchedulesEntity.getTotalPages(),
            pageOfSchedulesEntity.getTotalElements()
        );
    }

    @Override
    public Schedule save(Schedule schedule) {
        try {
            ScheduleEntity saved = scheduleRepository.save(mapper.toEntity(schedule));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            log.warning("Schedule " + schedule.getId() + " already exits in db");
            throw new ScheduleAlreadyExistsException();
        }
    }

    @Override
    public void update(Schedule schedule) {
        scheduleRepository.findById(schedule.getId()).ifPresent(scheduleEntity -> scheduleRepository.save(mapper.toEntity(schedule)));
    }

    @Override
    public void removeById(Integer id) {
        scheduleRepository.findById(id).ifPresent(scheduleEntity -> scheduleRepository.deleteById(id));
    }
}
