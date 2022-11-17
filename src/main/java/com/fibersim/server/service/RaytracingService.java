package com.fibersim.server.service;

import com.fibersim.core.model.common.Vector3;
import com.fibersim.core.model.condition.AndCondition;
import com.fibersim.core.model.condition.Condition;
import com.fibersim.core.model.condition.CylinderCondition;
import com.fibersim.core.model.condition.PlaneDistanceCondition;
import com.fibersim.core.model.element.*;
import com.fibersim.core.model.interphase.CylinderInterphase;
import com.fibersim.core.model.interphase.Interphase;
import com.fibersim.core.model.interphase.PlaneInterphase;
import com.fibersim.core.RaytracingSimulation;
import com.fibersim.core.simulator.RaytracingSimulator;
import com.fibersim.core.model.source.Source;
import com.fibersim.core.model.source.SunSource;
import com.fibersim.server.dto.RaytracingParamsDTO;
import com.fibersim.server.dto.RaytracingResultDTO;
import com.fibersim.server.mapper.RaytracingResultMapper;
import com.fibersim.core.model.detector.Detector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RaytracingService {
    @Autowired
    RaytracingSimulator raytracingSimulator;
    @Autowired
    RaytracingResultMapper raytracingResultMapper;
    @Autowired
    SimulationService simulationService;

    public RaytracingResultDTO getSimulation(String id) {
        RaytracingSimulation raytracingSimulation = simulationService.getSimulation(id);

        if(raytracingSimulation != null) {
            return raytracingResultMapper.map(raytracingSimulation);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<RaytracingResultDTO> getSimulations() {
        return raytracingResultMapper.map(simulationService.getSimulations());
    }

    @Async
    public void addSimulation(RaytracingParamsDTO paramsDTO) {
        double N = paramsDTO.getN();
        double sigmaAbs = paramsDTO.getSigmaAbs();
        double quantumYield = paramsDTO.getQuantumYield();
        double R = paramsDTO.getR();
        double L = paramsDTO.getL();
        double alpha = 0;
        double N_air = 1, N_PMMA = 1.492;

        Condition inCylinderCondition = new CylinderCondition(Vector3.O, Vector3.Z, R);
        Condition inZAxisCondition = new PlaneDistanceCondition(Vector3.O, Vector3.Z, L);
        Condition inFiberCondition = new AndCondition(inCylinderCondition, inZAxisCondition);

        Interphase leftEndInterphase = new PlaneInterphase(Vector3.O, Vector3.Z);
        Interphase rightEndInterphase = new PlaneInterphase(new Vector3(0, 0, L), Vector3.Z);
        Interphase cylinderInterphase = new CylinderInterphase(Vector3.O, Vector3.Z, R);

        Detector detector = new Detector();

        Element attenuatorElement = new AttenuatorElement(inFiberCondition, alpha);
        Element detectorElement = new DetectorElement(rightEndInterphase, detector, inCylinderCondition);
        Element dyeDopantElement = new DyeDopantElement(inFiberCondition, N, sigmaAbs, quantumYield);
        Element mirrorElement = new MirrorElement(leftEndInterphase, inCylinderCondition);
        Element refractorElement = new RefractorElement(cylinderInterphase, inZAxisCondition, N_air, N_PMMA);

        Source sunSource = new SunSource(new Vector3(-R, 2*R, 0), Vector3.X.scale(2*R), Vector3.Z.scale(L), 500e-9, 1e-3);

        RaytracingSimulation raytracingSimulation = new RaytracingSimulation();
        raytracingSimulation.getModel().addElements(List.of(attenuatorElement, detectorElement, dyeDopantElement, mirrorElement, refractorElement));
        raytracingSimulation.getModel().addSource(sunSource);
        raytracingSimulation.getModel().addDetector(detector);

        raytracingSimulator.simulate(raytracingSimulation);

        simulationService.addSimulation(raytracingSimulation);
    }
}
