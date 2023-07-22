package com.amigoscode.domain.email;

import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.email.PageEmail;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmailRepository {
    Optional<Email> findById(Integer id);

    PageEmail findAll(Pageable pageable);

    Email save(Email email);


}
