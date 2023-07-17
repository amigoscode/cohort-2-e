package com.amigoscode.domain.version;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class VersionService {
    private final VersionRepository versionRepository;


    public Version findById(Integer id){
        return versionRepository.findById(id).
                orElseThrow(VersionNotFoundException::new);
    }

    public PageVersion findAll(Pageable pageable) { return versionRepository.findAll(pageable); }

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
