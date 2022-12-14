package com.fibersim.core.raytracing.common;

import com.fibersim.core.TestProperties;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Vector3Test {
    @Autowired
    TestProperties testProperties;

    @Test
    public void vector3Test() {
        Vector3 vec1 = new Vector3(1.0, 0.0, 0.0);
        Vector3 scaled = vec1.scale(2);
        Vector3 unit = scaled.unit();
        Assertions.assertEquals(1.0, vec1.norm(), testProperties.DELTA);
        Assertions.assertEquals(2.0, scaled.norm(), testProperties.DELTA);
        Assertions.assertEquals(1.0, unit.norm(), testProperties.DELTA);

        Vector3 zeroUnit = Vector3.O.unit();
        Assertions.assertEquals(0.0, zeroUnit.norm(), testProperties.DELTA);

        Vector3 vec2 = new Vector3(0.0, 2.0, 2.0);

        Vector3 add = Vector3.add(vec1, vec2);
        Vector3 expectedAdd = new Vector3(1.0, 2.0, 2.0);
        Assertions.assertEquals(0.0, Vector3.sub(add, expectedAdd).norm(), testProperties.DELTA);

        double dot = Vector3.dot(vec1, vec2);
        double expectedDot = 0;
        Assertions.assertEquals(expectedDot, dot, testProperties.DELTA);

        Vector3 cross = Vector3.cross(vec1, vec2);
        Vector3 expectedCross = new Vector3(0, -2.0, 2.0);
        Assertions.assertEquals(0.0, Vector3.sub(cross, expectedCross).norm(), testProperties.DELTA);
    }

    @Test
    public void projectOnVectorTest() {
        Vector3 vector = new Vector3(3.0, 4.0, 5.0);
        Vector3 projectVector = Vector3.X;
        Vector3 expectedResult = new Vector3(3.0, 0.0, 0.0);

        Vector3 result = Vector3.projectOnVector(vector, projectVector);

        Assertions.assertEquals(0.0, Vector3.sub(result, expectedResult).norm(), testProperties.DELTA);
    }

    @Test
    public void projectOnSurfaceTest() {
        Vector3 vector = new Vector3(3.0, 4.0, 5.0);
        Vector3 normalVector = Vector3.X;
        Vector3 expectedResult = new Vector3(0.0, 4.0, 5.0);

        Vector3 result = Vector3.projectOnSurface(vector, normalVector);

        Assertions.assertEquals(0.0, Vector3.sub(result, expectedResult).norm(), testProperties.DELTA);
    }

    @Test
    public void reflectOnSurfaceTest() {
        Vector3 vector = new Vector3(3.0, 4.0, 5.0);
        Vector3 normalVector = Vector3.X;
        Vector3 expectedResult = new Vector3(-3.0, 4.0, 5.0);

        Vector3 result = Vector3.reflectOnSurface(vector, normalVector);

        Assertions.assertEquals(0.0, Vector3.sub(result, expectedResult).norm(), testProperties.DELTA);
    }

    @Test
    public void randomDirectionTest() {
        Vector3 result = Vector3.randomDirection();

        Assertions.assertEquals(1.0, result.norm(), testProperties.DELTA);
    }
}
