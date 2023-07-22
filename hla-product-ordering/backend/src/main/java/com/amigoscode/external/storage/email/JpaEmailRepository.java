package com.amigoscode.external.storage.email;

import com.amigoscode.external.storage.email.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmailRepository extends JpaRepository<EmailEntity,Integer> {

}
