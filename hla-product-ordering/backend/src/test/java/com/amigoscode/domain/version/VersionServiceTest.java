package com.amigoscode.domain.version;

import com.amigoscode.domain.note.NoteNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VersionServiceTest {
    @Mock
    private VersionRepository versionRepository;

    @InjectMocks
    private VersionService versionService;

    private static ZonedDateTime scheduledFor = ZonedDateTime.of(
            2023,
            6,
            17,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );

    private static ZonedDateTime received = ZonedDateTime.of(
            2023,
            6,
            16,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );
    private final Version version = new Version(
            1,
            3,
            4,
            1,
            scheduledFor,
            State.DONE,
            scheduledFor,
            scheduledFor,
            5,
            5
    );


    @Test
    void find_by_id_method_should_return_founded_version_when_version_exist() {
        Mockito.when(versionRepository.findByIdAndScheduleId(version.getId(), version.getScheduleId())).
                thenReturn(Optional.of(version));

        //when
        Version foundedVersion = versionService.findById(version.getId(), version.getScheduleId());

        //then
        Assertions.assertNotNull(foundedVersion);
        Assertions.assertEquals(version.getId(), foundedVersion.getId());
        Assertions.assertEquals(version.getScheduleId(), foundedVersion.getScheduleId());
        Assertions.assertEquals(version.getVersion(), foundedVersion.getVersion());
        Assertions.assertEquals(version.getQuantity(), foundedVersion.getQuantity());
        Assertions.assertEquals(version.getState(), foundedVersion.getState());
        Assertions.assertEquals(version.getEndDate(), foundedVersion.getEndDate());
        Assertions.assertEquals(version.getStartDate(), foundedVersion.getStartDate());
        Assertions.assertEquals(version.getSchedulePeriod(), foundedVersion.getSchedulePeriod());
    }

    @Test
    void find_by_id_method_should_throw_version_not_found_exception_when_version_does_not_exist() {
        Mockito.when(versionRepository.findByIdAndScheduleId(version.getId(), version.getScheduleId())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(VersionNotFoundException.class,
                () -> versionService.findById(version.getId(), version.getScheduleId()));
    }

    @Test
    void update_method_should_not_throw_exception() {
        Assertions.assertDoesNotThrow(() -> versionService.update(version));
    }


    @Test
    void delete_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> versionService.removeByScheduleId(version.getScheduleId()));
    }

    @Test
    void save_method_should_return_saved_version_when_version_does_not_exist() {
        Mockito.when(versionRepository.save(Mockito.any(Version.class))).thenReturn(version);
        //when
        Version savedVersion = versionService.save(version);
        //then
        Assertions.assertNotNull(savedVersion);
        Assertions.assertEquals(version.getId(), savedVersion.getId());
        Assertions.assertEquals(version.getScheduleId(), savedVersion.getScheduleId());
        Assertions.assertEquals(version.getVersion(), savedVersion.getVersion());
        Assertions.assertEquals(version.getQuantity(), savedVersion.getQuantity());
        Assertions.assertEquals(version.getState(), savedVersion.getState());
        Assertions.assertEquals(version.getEndDate(), savedVersion.getEndDate());
        Assertions.assertEquals(version.getStartDate(), savedVersion.getStartDate());
        Assertions.assertEquals(version.getSchedulePeriod(), savedVersion.getSchedulePeriod());
    }
}