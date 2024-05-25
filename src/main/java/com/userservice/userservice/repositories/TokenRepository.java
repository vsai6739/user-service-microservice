package com.userservice.userservice.repositories;

import com.userservice.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByValueAndDeleted(String tokenValue,Boolean isDeleted);
}
