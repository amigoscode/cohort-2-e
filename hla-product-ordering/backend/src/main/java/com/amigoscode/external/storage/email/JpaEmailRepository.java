package com.amigoscode.external.storage.email;

import com.amigoscode.external.storage.email.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmailRepository extends JpaRepository<EmailEntity,Integer> {

    Page<EmailEntity> findAllBySentAt(Pageable pageable, Integer sentAt);

}
