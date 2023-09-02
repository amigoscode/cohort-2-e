package com.amigoscode.appservices;

import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.email.EmailService;
import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.domain.schedule.PageSchedule;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleService;
import com.amigoscode.domain.schedule.Status;
import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleApplicationService {

    private final ScheduleService scheduleService;
    private final IAuthenticationFacade authenticationFacade;
    private final PatientService patientService;
    private final ProviderService providerService;
    private final EmailService emailService;
    private final VersionService versionService;

    @Transactional
    public Schedule findById(Integer id) {
        return scheduleService.findById(id);
    }

    @Transactional
    public PageSchedule findAll(Pageable pageable) {
        return scheduleService.findAll(pageable);
    }

    @Transactional
    public Schedule save(Schedule scheduleToSave) {
        return scheduleService.save(scheduleToSave, authenticationFacade.getLoggedInUserId());
    }

    @Transactional
    public void update(Schedule schedule) {
        scheduleService.update(schedule, authenticationFacade.getLoggedInUserId());

        if (schedule.getStatus() == Status.APPROVED_AND_EMAIL_SENT) {
            List<Version> versions = versionService.findAllVersionsByScheduleId(schedule.getId());
            versions.sort(Comparator.comparing(Version::getUpdatedAt).reversed());
            Version lastVersion = versions.get(0);

            final String emailContent = "Schedule id: " +
                    schedule.getId() +
                    ", MRN: " +
                    patientService.findById(schedule.getId()).getMrn() +
                    ", Start date: " +
                    lastVersion.getStartDate() +
                    ", End date: " +
                    lastVersion.getEndDate() +
                    ", Quantity: " +
                    lastVersion.getQuantity() +
                    ", Period: " +
                    lastVersion.getSchedulePeriod() +
                    ".";

            Email email = new Email(
                    null,
                    providerService.getPreferredProvider().getId(),
                    ZonedDateTime.now(),
                    null,
                    schedule.getId(),
                    authenticationFacade.getLoggedInUserId(),
                    emailContent
            );
            emailService.save(email,authenticationFacade.getLoggedInUserId());
        }
    }

    @Transactional
    public void removeById(Integer id) {
        scheduleService.removeById(id);
    }
}

