package com.amigoscode.api.schedule;


import com.amigoscode.appservices.ScheduleApplicationService;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/schedules"
)
class ScheduleController {
    private final ScheduleApplicationService scheduleService;

    private final ScheduleDtoMapper scheduleMapper;

    private final PageScheduleDtoMapper pageScheduleDtoMapper;

    @GetMapping
    public ResponseEntity<PageScheduleDto> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageScheduleDto pageOrders = pageScheduleDtoMapper.toPageDto(scheduleService.findAll(pageable));

        return ResponseEntity.ok(pageOrders);
    }

    @GetMapping( path = "/{scheduleId}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable Integer scheduleId) {
        Schedule schedule = scheduleService.findById(scheduleId);
        return ResponseEntity
                .ok(scheduleMapper.toDto(schedule));
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> saveSchedule(@RequestBody ScheduleDto dto) {

        Schedule schedule = scheduleService.save(scheduleMapper.toDomain(dto));
        return ResponseEntity
                .ok(scheduleMapper.toDto(schedule));
    }

    @PutMapping
    public ResponseEntity<Void> updateSchedule(@RequestBody ScheduleDto dto) {
        scheduleService.update(scheduleMapper.toDomain(dto));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeSchedule(@PathVariable Integer id){
        scheduleService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
