package com.amigoscode.api.version;


import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/schedules/{scheduleId}/versions",
        produces = "application/json",
        consumes = "application/json"
)
class VersionController {
    private final VersionService versionService;

    private final VersionDtoMapper versionMapper;

    private final PageVersionDtoMapper pageVersionDtoMapper;

    @GetMapping
    public ResponseEntity<PageVersionDto> getVersions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @PathVariable Integer scheduleId) {
        Pageable pageable = PageRequest.of(page, size);
        PageVersionDto pageVersions = pageVersionDtoMapper.toPageDto(versionService.findAll(pageable, scheduleId));
        return ResponseEntity.ok(pageVersions);
    }

    @GetMapping(path = "/{versionId}")
    public ResponseEntity<VersionDto> getVersion(@PathVariable Integer scheduleId, @PathVariable Integer versionId) {
        Version version = versionService.findById(versionId, scheduleId);
        return ResponseEntity
                .ok(versionMapper.toDto(version));
    }

}
