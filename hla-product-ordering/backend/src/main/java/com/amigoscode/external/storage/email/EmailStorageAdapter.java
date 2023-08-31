package com.amigoscode.external.storage.email;

import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.email.EmailAlreadyExistsException;
import com.amigoscode.domain.email.EmailRepository;
import com.amigoscode.domain.email.PageEmail;
import com.amigoscode.external.storage.email.JpaEmailRepository;
import com.amigoscode.external.storage.email.EmailEntity;
import com.amigoscode.external.storage.email.EmailEntityMapper;
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
public class EmailStorageAdapter implements EmailRepository {
    private final JpaEmailRepository emailRepository;

    private final EmailEntityMapper mapper;


    @Override
    public Optional<Email> findById(final Integer id) {
        return emailRepository.findById(id).map(mapper::toDomain);
    }



    @Override
    public PageEmail findAll(Pageable pageable) {
        Page<EmailEntity> pageOfEmailsEntity = emailRepository.findAll(pageable);
        List<Email> emailsOnCurrentPage = pageOfEmailsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageEmail(
            emailsOnCurrentPage,
            pageable.getPageNumber() +1,
            pageOfEmailsEntity.getTotalPages(),
            pageOfEmailsEntity.getTotalElements()
        );
    }

    @Override
    public PageEmail findUnsent(final Pageable pageable) {
        Page<EmailEntity> pageOfEmailsEntity = emailRepository.findAllBySentAt(pageable, null);
        List<Email> emailsOnCurrentPage = pageOfEmailsEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageEmail(
                emailsOnCurrentPage,
                pageable.getPageNumber() +1,
                pageOfEmailsEntity.getTotalPages(),
                pageOfEmailsEntity.getTotalElements()
        );
    }

    @Override
    public Email save(Email email) {
        try {
            EmailEntity saved = emailRepository.save(mapper.toEntity(email));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            log.warning("DataIntegrityViolationException: " + ex);
            log.warning("Email " + email.getId() + " already exits in db");
            throw new EmailAlreadyExistsException();
        }
    }

}
