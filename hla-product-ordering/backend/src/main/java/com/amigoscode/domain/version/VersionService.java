package com.amigoscode.domain.version;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class VersionService {
    private final VersionRepository versionRepository;

    public Version findById(Integer id, Integer scheduleId){
        return versionRepository.findByIdAndScheduleId(id, scheduleId).
                orElseThrow(VersionNotFoundException::new);
    }
    public List<Version> findAllVersionsByScheduleId(Integer scheduleId){
        return versionRepository.findByScheduleId(scheduleId);
    }

    public PageVersion findAll(Pageable pageable, Integer scheduleId) { return versionRepository.findAllByScheduleId(pageable, scheduleId); }

    public Version save(Version version) {
        return versionRepository.save(version);
    }

    public void update(Version version){
        versionRepository.update(version);
    }

    public void removeByScheduleId(Integer id){
        versionRepository.removeByScheduleId(id);
    }

}
