package ru.itmo.web4.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.web4.dto.PointInDto;
import ru.itmo.web4.dto.PointOutDto;
import ru.itmo.web4.model.Point;
import ru.itmo.web4.repository.PointRepository;
import ru.itmo.web4.service.PointService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    @Override
    public List<PointOutDto> findAllPointsById(Long id) {
        List<Point> points = pointRepository.findAllByUserId(id);
        List<PointOutDto> pointOutDtos = new ArrayList<>();
        for (Point point: points) {
           pointOutDtos.add(0, mapToPointOutDto(point));
        }
        return pointOutDtos;
    }

    @Override
    public List<PointOutDto> addPointWithId(PointInDto pointDto, Long id) {
        String time = getTime();
        long scriptStartTime = System.nanoTime();
        String hit = getHit(pointDto.getX(), pointDto.getY(), pointDto.getR());
        long scriptEndTime = System.nanoTime();
        long scriptDuration = (scriptEndTime - scriptStartTime);
        Point point = pointRepository.save(mapToPoint(pointDto, hit, time, scriptDuration, id));
        return this.findAllPointsById(id);
    }

    @Override
    public long deleteWithId(Long id) {
        long answer = pointRepository.deleteByUserId(id);
        return answer;
    }

    private PointOutDto mapToPointOutDto(Point point) {
        PointOutDto pointOutDto = PointOutDto.builder()
                .id(point.getId())
                .x(point.getX())
                .y(point.getY())
                .r(point.getR())
                .hit(point.getHit())
                .time(point.getTime())
                .scriptDuration(point.getScriptDuration())
                .build();
        return pointOutDto;
    }

    private Point mapToPoint(PointInDto pointDto, String hit, String time, Long dur, Long id){
        Point point = Point.builder()
                .x(pointDto.getX())
                .y(pointDto.getY())
                .r(pointDto.getR())
                .hit(hit)
                .time(time)
                .scriptDuration(dur)
                .userId(id)
                .build();
        return point;
    }

    private String getTime() {
        LocalDateTime attemptTime = LocalDateTime.now();
        final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = attemptTime.format(CUSTOM_FORMATTER);
        return time;
    }

    private String getHit(float x, float y, float r) {
        String hit = "Промах";
        if (this.checkHit(x, y, r)) hit = "Попадание";
        return hit;
    }

    private boolean checkHit(float x, float y, float r) {
        return (x <= 0 && y <= 0 && Math.pow(r / 2, 2) - Math.pow(x, 2) - Math.pow(y, 2) >= 0) ||
                (x >= 0 && y <= 0 && x <= r && y >= -r) || (x <= 0 && y >= 0 && x >= -r /2 && y <= r / 2 + x);
    }
}
