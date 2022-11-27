package com.fibersim.server.service;

import com.fibersim.core.resources.medium.Medium;
import com.fibersim.core.raytracing.common.Vector3;
import com.fibersim.core.raytracing.condition.*;
import com.fibersim.core.resources.dopant.DyeDopant;
import com.fibersim.core.raytracing.element.*;
import com.fibersim.core.raytracing.interphase.CylinderInterphase;
import com.fibersim.core.raytracing.interphase.Interphase;
import com.fibersim.core.raytracing.interphase.PlaneInterphase;
import com.fibersim.core.simulation.RaytracingSimulation;
import com.fibersim.core.raytracing.wavelength.function.ConstantWFunction;
import com.fibersim.core.raytracing.wavelength.provider.DefaultWavelengthProvider;
import com.fibersim.core.raytracing.wavelength.provider.WavelengthProvider;
import com.fibersim.core.raytracing.wavelength.spectrum.WavelengthSpectrum;
import com.fibersim.core.simulation.RaytracingSimulator;
import com.fibersim.core.raytracing.source.Source;
import com.fibersim.core.raytracing.source.SunSource;
import com.fibersim.resources.reader.DyeDopantReader;
import com.fibersim.resources.reader.MediumReader;
import com.fibersim.server.dto.RaytracingParamsDTO;
import com.fibersim.server.dto.RaytracingResultDTO;
import com.fibersim.server.mapper.RaytracingResultMapper;
import com.fibersim.core.raytracing.detector.Detector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    MediumReader mediumReader;
    @Autowired
    DyeDopantReader dyeDopantReader;

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

    public void addSimulation(RaytracingParamsDTO paramsDTO) {
        double concentration = paramsDTO.getN();
        double R = paramsDTO.getR();
        double L = paramsDTO.getL();

        //TODO Add support for data obtained from CSV files instead of constants

        WavelengthProvider wavelengthProvider = new DefaultWavelengthProvider(440e-9, 740e-9, 100);

        Medium mediumAir = mediumReader.read("Air");
        Medium mediumPMMA = mediumReader.read("PMMA");

        DyeDopant dyeDopant = dyeDopantReader.read("Rh6G");

        Interphase leftEndInterphase = new PlaneInterphase(Vector3.O, Vector3.Z);
        Interphase rightEndInterphase = new PlaneInterphase(new Vector3(0, 0, L), Vector3.Z);
        Interphase cylinderInterphase = new CylinderInterphase(Vector3.O, Vector3.Z, R);

        Condition inCylinderCondition = new CylinderCondition(Vector3.O, Vector3.Z, R);
        Condition inZAxisCondition = new PlaneDistanceCondition(Vector3.O, Vector3.Z, L);
        Condition inFiberCondition = new AndCondition(inCylinderCondition, inZAxisCondition);
        Condition directionCondition = new DirectionCondition(rightEndInterphase);
        Condition detectorCondition = new AndCondition(inCylinderCondition, directionCondition);

        Detector detector = new Detector(wavelengthProvider);

        Element attenuatorElement = new AttenuatorElement(inFiberCondition, mediumPMMA);
        Element detectorElement = new DetectorElement(rightEndInterphase, detector, detectorCondition);
        //Element dyeDopantElement = new DyeDopantElement(inFiberCondition, dyeDopant, concentration, wavelengthProvider);
        Element dyeDopantElement = new VariableSpectrumDyeDopantElement(inFiberCondition, dyeDopant, concentration, wavelengthProvider);
        Element mirrorElement = new MirrorElement(leftEndInterphase, inCylinderCondition);
        Element refractorElement = new RefractorElement(cylinderInterphase, inZAxisCondition, mediumAir, mediumPMMA);

        Source sunSource = new SunSource(new Vector3(-R, 2*R, 0), Vector3.X.scale(2*R), Vector3.Z.scale(L),
                new WavelengthSpectrum(wavelengthProvider, new ConstantWFunction(1)),
                1200*R*L);

        RaytracingSimulation raytracingSimulation = new RaytracingSimulation();
        raytracingSimulation.getModel().addElements(List.of(attenuatorElement, detectorElement, dyeDopantElement, mirrorElement, refractorElement));
        raytracingSimulation.getModel().addSource(sunSource);
        raytracingSimulation.getModel().addDetector(detector);

        raytracingSimulator.simulate(raytracingSimulation);

        simulationService.addSimulation(raytracingSimulation);
    }
}
