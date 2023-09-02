package com.amigoscode.appservices;

import com.amigoscode.domain.email.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log
public class EmailApplicationService {
    private final EmailService emailService;
    private final IAuthenticationFacade authenticationFacade;
    public Email findById(Integer id){
        return emailService.findById(id);
    }

    public PageEmail findAll(Pageable pageable) { return emailService.findAll(pageable); }

    public PageEmail findUnsent(Pageable pageable) { return emailService.findUnsent(pageable); }

    @Transactional
    public Email saveTransaction(Email emailToSave){
        return emailService.save(emailToSave, authenticationFacade.getLoggedInUserId());
    }

    public Email save(Email emailToSave) {
        try {
            return saveTransaction(emailToSave);
        } catch (DataIntegrityViolationException ex){
            log.warning("Email " + emailToSave.toString() + " already exits in db");
            throw new EmailAlreadyExistsException();
        }
    }

    public void send(Email email) {
        emailService.send(email);
    }


}
