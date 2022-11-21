package com.fibersim.core.raytracing.source;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;
import com.fibersim.core.raytracing.wavelength.Wavelength;
import com.fibersim.core.raytracing.wavelength.spectrum.WavelengthSpectrum;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SunSource implements Source {
    private final static int M_X = 100;
    private final static int M_Z = 100;
    private final static int M_L = 1000;
    private final static int M = M_X*M_Z*M_L;

    private final Vector3 origin;
    private final Vector3 axis1;
    private final Vector3 axis2;
    private final WavelengthSpectrum solarSpectrum;
    private final double totalPower;

    @Override
    public int numRays() {
        return M;
    }

    @Override
    public Ray getRay(int index) {
        int indexX = (index/M_Z/M_L)%M_X;
        int indexZ = (index/M_L)%M_Z;

        double posX = (indexX+0.5)/M_X;
        double posZ = (indexZ+0.5)/M_Z;

        Vector3 relativePos = Vector3.add(this.axis1.scale(posX), this.axis2.scale(posZ));
        Vector3 pos = Vector3.add(this.origin, relativePos);
        Vector3 vel = Vector3.cross(this.axis1, this.axis2).unit();
        Wavelength lambda = this.solarSpectrum.generateWavelength();
        double power = this.totalPower/this.numRays();

        return new Ray(pos, vel, lambda, power);
    }
}
