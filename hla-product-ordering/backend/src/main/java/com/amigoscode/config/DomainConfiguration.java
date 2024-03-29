package com.amigoscode.config;

import com.amigoscode.domain.email.EmailRepository;
import com.amigoscode.domain.email.EmailSender;
import com.amigoscode.domain.email.EmailService;
import com.amigoscode.domain.note.NoteRepository;
import com.amigoscode.domain.note.NoteService;
import com.amigoscode.domain.patient.PatientRepository;
import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.domain.provider.ProviderRepository;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.domain.schedule.ScheduleRepository;
import com.amigoscode.domain.user.EncodingService;
import com.amigoscode.domain.user.UserRepository;
import com.amigoscode.domain.user.UserService;
import com.amigoscode.domain.version.VersionRepository;
import com.amigoscode.domain.version.VersionService;
import com.amigoscode.external.email.EmailSenderIAdapter;
import com.amigoscode.external.storage.email.EmailEntityMapper;
import com.amigoscode.external.storage.email.EmailStorageAdapter;
import com.amigoscode.external.storage.email.JpaEmailRepository;
import com.amigoscode.external.storage.note.JpaNoteRepository;
import com.amigoscode.external.storage.note.NoteEntityMapper;
import com.amigoscode.external.storage.note.NoteStorageAdapter;
import com.amigoscode.external.storage.patient.ListPatientStorageAdapter;
import com.amigoscode.external.storage.patient.PatientEntityMapper;
import com.amigoscode.external.storage.provider.JpaProviderRepository;
import com.amigoscode.external.storage.provider.ProviderEntityMapper;
import com.amigoscode.external.storage.provider.ProviderStorageAdapter;
import com.amigoscode.external.storage.schedule.JpaScheduleRepository;
import com.amigoscode.external.storage.schedule.ScheduleEntityMapper;
import com.amigoscode.external.storage.schedule.ScheduleStorageAdapter;
import com.amigoscode.external.storage.user.JpaUserRepository;
import com.amigoscode.external.storage.user.UserEntityMapper;
import com.amigoscode.external.storage.user.UserStorageAdapter;
import com.amigoscode.external.storage.version.JpaVersionRepository;
import com.amigoscode.external.storage.version.VersionEntityMapper;
import com.amigoscode.external.storage.version.VersionStorageAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Clock;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository, UserEntityMapper mapper) {
        return new UserStorageAdapter(jpaUserRepository, mapper);
    }

    @Bean
    public UserService userService(UserRepository userRepository, EncodingService encoder, Clock clock)  {
        return new UserService(userRepository, encoder, clock);
    }

    @Bean
    public ProviderRepository providerRepository(JpaProviderRepository jpaProviderRepository, ProviderEntityMapper mapper) {
        return new ProviderStorageAdapter(jpaProviderRepository, mapper);
    }

    @Bean
    public ProviderService providerService(ProviderRepository providerRepository, Clock clock)  {
        return new ProviderService(providerRepository, clock);
    }

    @Bean
    public EmailRepository emailRepository(JpaEmailRepository jpaEmailRepository, EmailEntityMapper mapper){
        return new EmailStorageAdapter(jpaEmailRepository, mapper);
    }

    @Bean
    public EmailSender emailSender(JavaMailSender javaMailSender, ProviderService providerService){
        return new EmailSenderIAdapter(javaMailSender, providerService);
    }

    @Bean
    public EmailService emailService(EmailRepository emailRepository, EmailSender emailSender, Clock clock){
        return new EmailService(emailRepository, emailSender, clock);
    }
    @Bean
    public ScheduleRepository scheduleRepository(JpaScheduleRepository jpaScheduleRepository, ScheduleEntityMapper mapper){
        return new ScheduleStorageAdapter(jpaScheduleRepository, mapper);
    }

    @Bean
    public com.amigoscode.domain.schedule.ScheduleService scheduleService(ScheduleRepository scheduleRepository,
                                                                          VersionService versionService,
                                                                          NoteService noteService,
                                                                          PatientService patientService){
        return new com.amigoscode.domain.schedule.ScheduleService(scheduleRepository,
                versionService,
                noteService,
                patientService);
    }

    @Bean
    public VersionRepository versionRepository(JpaVersionRepository jpaVersionRepository, VersionEntityMapper mapper){
        return new VersionStorageAdapter(jpaVersionRepository, mapper);
    }

    @Bean
    public VersionService versionService(VersionRepository versionRepository){
        return new VersionService(versionRepository);
    }

    @Bean
    public NoteRepository noteRepository(JpaNoteRepository jpaNoteRepository, NoteEntityMapper mapper){
        return new NoteStorageAdapter(jpaNoteRepository, mapper);
    }

    @Bean
    public NoteService noteService(NoteRepository noteRepository){
        return new NoteService(noteRepository);
    }

    @Bean
    public PatientRepository patientRepository(PatientEntityMapper mapper) {
        return new ListPatientStorageAdapter(mapper);
    }

    @Bean
    public PatientService patientService(PatientRepository patientRepository, Clock clock)  {
        return new PatientService(patientRepository, clock);
    }


}
