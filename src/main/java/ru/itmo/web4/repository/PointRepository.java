package ru.itmo.web4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.web4.model.Point;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByUserId(Long id);
    long deleteByUserId(Long id);
}
