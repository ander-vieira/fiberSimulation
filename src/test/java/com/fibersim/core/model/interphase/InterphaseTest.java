package com.fibersim.core.model.interphase;

import com.fibersim.core.TestProperties;
import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.common.Vector3;
import com.fibersim.core.model.wavelength.Wavelength;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InterphaseTest {
    @Autowired
    TestProperties testProperties;

    @Test
    public void cylinderInterphaseTest() {
        Interphase cylinderInterphase = new CylinderInterphase(Vector3.O, Vector3.Z, 1);

        //Test intersect
        Wavelength wavelength = new Wavelength(1, 500e-9);
        double power = 1e-3;
        Ray ray1 = new Ray(Vector3.O, Vector3.X, wavelength, power);
        Ray ray2 = new Ray(Vector3.X.scale(2), Vector3.X, wavelength, power);
        Ray ray3 = new Ray(Vector3.X.scale(-2), Vector3.X, wavelength, power);
        Ray ray4 = new Ray(Vector3.O, Vector3.Z, wavelength, power);
        Ray ray5 = new Ray(Vector3.Y.scale(2), Vector3.X, wavelength, power);

        Assertions.assertEquals(1.0, cylinderInterphase.intersect(ray1), testProperties.DELTA);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, cylinderInterphase.intersect(ray2));
        Assertions.assertEquals(1.0, cylinderInterphase.intersect(ray3), testProperties.DELTA);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, cylinderInterphase.intersect(ray4));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, cylinderInterphase.intersect(ray5));

        //Test getNormalVector
        Vector3 normalVector = cylinderInterphase.getNormalVector(Vector3.X);
        Assertions.assertEquals(0.0, Vector3.sub(normalVector, Vector3.X).norm(), testProperties.DELTA);
    }

    @Test
    public void planeInterphaseTest() {
        Interphase planeInterphase = new PlaneInterphase(Vector3.Z, Vector3.Z);

        //Test intersect
        Wavelength wavelength = new Wavelength(1, 500e-9);
        double power = 1e-3;

        Ray ray1 = new Ray(Vector3.O, Vector3.Z, wavelength, power);
        Ray ray2 = new Ray(Vector3.O, Vector3.X, wavelength, power);
        Ray ray3 = new Ray(Vector3.Z.scale(2), Vector3.Z, wavelength, power);

        Assertions.assertEquals(1.0, planeInterphase.intersect(ray1), testProperties.DELTA);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, planeInterphase.intersect(ray2));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, planeInterphase.intersect(ray3));

        //Test getNormalVector
        Vector3 normalVector = planeInterphase.getNormalVector(Vector3.O);
        Assertions.assertEquals(0.0, Vector3.sub(normalVector, Vector3.Z).norm());
    }
}
