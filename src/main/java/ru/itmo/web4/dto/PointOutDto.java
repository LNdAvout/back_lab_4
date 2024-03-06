package ru.itmo.web4.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointOutDto {
    private Long id;
    private float x;
    private float y;
    private float r;
    private String hit;
    private String time;
    private long scriptDuration;
}
