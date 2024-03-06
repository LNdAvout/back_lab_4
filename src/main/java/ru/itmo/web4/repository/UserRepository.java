package ru.itmo.web4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.web4.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);
}
