package com.fibersim.core.model.common;

import com.fibersim.core.TestProperties;
import com.fibersim.core.model.wavelength.Wavelength;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RayTest {
    @Autowired
    TestProperties testProperties;

    @Test
    public void rayTest() {
        Vector3 pos = Vector3.O;
        Vector3 vel = Vector3.X;
        Wavelength wavelength = new Wavelength(1, 500e-9);
        double power = 1e-3;

        //Test constructor and getters
        Ray ray = new Ray(pos, vel, wavelength, power);
        Assertions.assertEquals(pos, ray.getPos());
        Assertions.assertEquals(1.0, ray.getVel().norm(), testProperties.DELTA);
        Assertions.assertEquals(wavelength, ray.getWavelength());
        Assertions.assertEquals(power, ray.getPower(), testProperties.DELTA);
        Assertions.assertTrue(ray.alive());

        //Test move
        double ds = 1.0;
        ray.move(ds);
        Assertions.assertEquals(ds, Vector3.sub(ray.getPos(), pos).norm(), testProperties.DELTA);

        //Test setVel
        ray.setVel(Vector3.Y.scale(2));
        Assertions.assertEquals(1.0, ray.getVel().norm(), testProperties.DELTA);

        //Test setWavelength
        Wavelength newWavelength = new Wavelength(2, 600e-9);
        double newPower = power*wavelength.getValue()/newWavelength.getValue();
        ray.setWavelength(newWavelength);
        Assertions.assertEquals(newWavelength, ray.getWavelength());
        Assertions.assertEquals(newPower, ray.getPower(), testProperties.DELTA);

        //Test kill
        ray.kill();
        Assertions.assertFalse(ray.alive());
    }
}
