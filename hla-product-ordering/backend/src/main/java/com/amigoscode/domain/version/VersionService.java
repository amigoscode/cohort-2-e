package com.amigoscode.domain.version;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class VersionService {
    private final VersionRepository versionRepository;


    public Version findById(Integer id, Integer scheduleId){
        return versionRepository.findByIdAndScheduleId(id, scheduleId).
                orElseThrow(VersionNotFoundException::new);
    }

    public PageVersion findAll(Pageable pageable, Integer scheduleId) { return versionRepository.findAllByScheduleId(pageable, scheduleId); }

    public Version save(Version version) {
        return versionRepository.save(version);
    }

    public void update(Version version){
        versionRepository.update(version);
    }

    public void removeById(Integer id){
        versionRepository.removeById(id);
    }

}
