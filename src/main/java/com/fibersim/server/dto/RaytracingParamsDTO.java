package com.fibersim.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RaytracingParamsDTO {
    private double N;
    private double sigmaAbs;
    private double quantumYield;
    private double R;
    private double L;
}
