package com.fibersim.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RaytracingResultDTO {
    private String id;
    private boolean finished;
    private int progress;
    private double lightPower;
}
