package com.amigoscode.external.storage.version;

import com.amigoscode.domain.version.PageVersion;
import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionAlreadyExistsException;
import com.amigoscode.domain.version.VersionRepository;
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
public class VersionStorageAdapter implements VersionRepository {
    private final JpaVersionRepository versionRepository;

    private final VersionEntityMapper mapper;


    @Override
    public Optional<Version> findByIdAndScheduleId(final Integer id, final Integer scheduleId) {
        return versionRepository.findByIdAndScheduleId(id, scheduleId).map(mapper::toDomain);
    }

    @Override
    public List<Version> findByScheduleId(Integer scheduleId) {
        return versionRepository.findByScheduleId(scheduleId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public PageVersion findAllByScheduleId(Pageable pageable, final Integer scheduleId) {
        Page<VersionEntity> pageOfVersionsEntity = versionRepository.findAllByScheduleId(pageable, scheduleId);
        List<Version> versionsOnCurrentPage = pageOfVersionsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageVersion(
            versionsOnCurrentPage,
            pageable.getPageNumber() +1,
            pageOfVersionsEntity.getTotalPages(),
            pageOfVersionsEntity.getTotalElements()
        );
    }

    @Override
    public Version save(Version version) {
        try {
            VersionEntity saved = versionRepository.save(mapper.toEntity(version));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            log.warning("Version " + version.getId() + " already exits in db");
            throw new VersionAlreadyExistsException();
        }
    }

    @Override
    public void update(Version version) {
        versionRepository.findById(version.getId()).ifPresent(versionEntity -> versionRepository.save(mapper.toEntity(version)));
    }

    @Override
    public void removeById(Integer id) {
        versionRepository.findById(id).ifPresent(versionEntity -> versionRepository.deleteById(id));
    }

    @Override
    public void removeByScheduleId(Integer scheduleId) {
        versionRepository.findByScheduleId(scheduleId)
                .forEach(versionEntity -> versionRepository.deleteById(versionEntity.getId()));

    }
}
