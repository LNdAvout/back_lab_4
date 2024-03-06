package ru.itmo.web4.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.web4.config.JwtService;
import ru.itmo.web4.dto.PointInDto;
import ru.itmo.web4.dto.PointOutDto;
import ru.itmo.web4.model.Point;
import ru.itmo.web4.service.PointService;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final JwtService jwtService;
    private final PointService pointService;

    @GetMapping("/say")
    public ResponseEntity<String> sayHello(
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        Long id = Long.parseLong(jwtService.extractIdent(jwt));
        //String answer = pointService.deleteWithId(id);
        return ResponseEntity.ok("Hello from security" + " " + id);
    }

    @GetMapping("/get")
    public ResponseEntity<List<PointOutDto>> getPoints(
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        Long id = Long.parseLong(jwtService.extractIdent(jwt));
        List<PointOutDto> points = pointService.findAllPointsById(id);
        return ResponseEntity.ok(points);
    }

    @PostMapping("/add")
    public ResponseEntity<List<PointOutDto>> addPoint(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody PointInDto newPointDto
    ) {
        String jwt = authHeader.substring(7);
        Long id = Long.parseLong(jwtService.extractIdent(jwt));
        List<PointOutDto> points = pointService.addPointWithId(newPointDto, id);
        return ResponseEntity.ok(points);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> deletePoints(
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        Long id = Long.parseLong(jwtService.extractIdent(jwt));
        Long answer = pointService.deleteWithId(id);
        return ResponseEntity.ok(answer);
//        long a = 10;
//        return ResponseEntity.ok(a);
    }


}
