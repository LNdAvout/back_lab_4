package ru.itmo.web4.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointInDto {
    private float x;
    private float y;
    private float r;
}
