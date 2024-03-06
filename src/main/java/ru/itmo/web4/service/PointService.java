package ru.itmo.web4.service;

import ru.itmo.web4.dto.PointInDto;
import ru.itmo.web4.dto.PointOutDto;
import ru.itmo.web4.model.Point;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PointService {
    List<PointOutDto> findAllPointsById(Long id);

    List<PointOutDto> addPointWithId(PointInDto pointDto, Long id);
    @Transactional
    long deleteWithId(Long id);
}
