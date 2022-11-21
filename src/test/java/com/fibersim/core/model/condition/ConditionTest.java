package com.fibersim.core.model.condition;

import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.common.Vector3;
import com.fibersim.core.model.interphase.Interphase;
import com.fibersim.core.model.wavelength.Wavelength;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConditionTest {
    @Test
    public void alwaysConditionTest() {
        Condition condition = Condition.ALWAYS;

        Assertions.assertTrue(condition.check(null));
    }
    @Test
    public void andConditionTest() {
        Condition condition1 = Mockito.mock(Condition.class);
        Condition condition2 = Mockito.mock(Condition.class);
        Condition andCondition = new AndCondition(condition1, condition2);

        Mockito.when(condition1.check(Mockito.nullable(Ray.class))).thenReturn(true);
        Mockito.when(condition2.check(Mockito.nullable(Ray.class))).thenReturn(true);

        Assertions.assertTrue(andCondition.check(null));

        Mockito.when(condition2.check(Mockito.nullable(Ray.class))).thenReturn(false);

        Assertions.assertFalse(andCondition.check(null));

        Mockito.when(condition1.check(Mockito.nullable(Ray.class))).thenReturn(false);

        Assertions.assertFalse(andCondition.check(null));
    }
    @Test
    public void orConditionTest() {
        Condition condition1 = Mockito.mock(Condition.class);
        Condition condition2 = Mockito.mock(Condition.class);
        Condition orCondition = new OrCondition(condition1, condition2);

        Mockito.when(condition1.check(Mockito.nullable(Ray.class))).thenReturn(true);
        Mockito.when(condition2.check(Mockito.nullable(Ray.class))).thenReturn(true);

        Assertions.assertTrue(orCondition.check(null));

        Mockito.when(condition2.check(Mockito.nullable(Ray.class))).thenReturn(false);

        Assertions.assertTrue(orCondition.check(null));

        Mockito.when(condition1.check(Mockito.nullable(Ray.class))).thenReturn(false);

        Assertions.assertFalse(orCondition.check(null));
    }
    @Test
    public void notConditionTest() {
        Condition condition1 = Mockito.mock(Condition.class);
        Condition notCondition = new NotCondition(condition1);

        Mockito.when(condition1.check(Mockito.nullable(Ray.class))).thenReturn(true);

        Assertions.assertFalse(notCondition.check(null));

        Mockito.when(condition1.check(Mockito.nullable(Ray.class))).thenReturn(false);

        Assertions.assertTrue(notCondition.check(null));
    }

    @Test
    public void cylinderConditionTest() {
        Condition cylinderCondition = new CylinderCondition(Vector3.O, Vector3.Z, 1);

        Vector3 vel = Vector3.Z;
        Wavelength wavelength = new Wavelength(1, 500e-9);
        double power = 1e-3;
        Ray ray1 = new Ray(Vector3.O, vel, wavelength, power);
        Ray ray2 = new Ray(Vector3.X.scale(2), vel, wavelength, power);

        Assertions.assertTrue(cylinderCondition.check(ray1));
        Assertions.assertFalse(cylinderCondition.check(ray2));
    }

    @Test
    public void planeDistanceConditionTest() {
        Condition planeDistanceCondition = new PlaneDistanceCondition(Vector3.O, Vector3.Z, 1);

        Vector3 vel = Vector3.Z;
        Wavelength wavelength = new Wavelength(1, 500e-9);
        double power = 1e-3;
        Ray ray1 = new Ray(Vector3.Z.scale(0.5), vel, wavelength, power);
        Ray ray2 = new Ray(Vector3.Z.scale(2), vel, wavelength, power);

        Assertions.assertTrue(planeDistanceCondition.check(ray1));
        Assertions.assertFalse(planeDistanceCondition.check(ray2));
    }

    @Test
    public void directionCondition() {
        Interphase interphase = Mockito.mock(Interphase.class);
        Condition directionCondition = new DirectionCondition(interphase);

        Wavelength wavelength = new Wavelength(1, 500e-9);
        double power = 1e-3;
        Ray ray = new Ray(Vector3.O, new Vector3(1.0, -1.0, 1.0), wavelength, power);

        Mockito.when(interphase.getNormalVector(Mockito.nullable(Vector3.class))).thenReturn(Vector3.Z);

        Assertions.assertTrue(directionCondition.check(ray));

        Mockito.when(interphase.getNormalVector(Mockito.nullable(Vector3.class))).thenReturn(Vector3.Y);

        Assertions.assertFalse(directionCondition.check(ray));
    }
}
