package com.fibersim.core.model;

import com.fibersim.core.model.detector.Detector;
import com.fibersim.core.model.element.Element;
import com.fibersim.core.model.source.Source;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RaytracingModel {
    @Getter
    private final List<Element> elements;
    @Getter
    private final List<Source> sources;
    @Getter
    private Detector detector;

    public RaytracingModel() {
        this.elements = new ArrayList<>();
        this.sources = new ArrayList<>();
        this.detector = null;
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public void addElements(Collection<? extends Element> c) {
        elements.addAll(c);
    }

    public void addSource(Source source) {
        sources.add(source);
    }

    public void addSources(Collection<? extends Source> c) {
        sources.addAll(c);
    }

    public void addDetector(Detector detector) {
        this.detector = detector;
    }
}
