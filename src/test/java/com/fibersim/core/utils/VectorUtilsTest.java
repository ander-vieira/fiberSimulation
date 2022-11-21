package com.fibersim.core.utils;

import com.fibersim.core.TestProperties;
import com.fibersim.core.model.common.Vector3;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VectorUtilsTest {
    @Autowired
    TestProperties testProperties;

    @Test
    public void projectOnVectorTest() {
        Vector3 vector = new Vector3(3.0, 4.0, 5.0);
        Vector3 projectVector = Vector3.X;
        Vector3 expectedResult = new Vector3(3.0, 0.0, 0.0);

        Vector3 result = VectorUtils.projectOnVector(vector, projectVector);

        Assertions.assertEquals(0.0, Vector3.sub(result, expectedResult).norm(), testProperties.DELTA);
    }

    @Test
    public void projectOnSurfaceTest() {
        Vector3 vector = new Vector3(3.0, 4.0, 5.0);
        Vector3 normalVector = Vector3.X;
        Vector3 expectedResult = new Vector3(0.0, 4.0, 5.0);

        Vector3 result = VectorUtils.projectOnSurface(vector, normalVector);

        Assertions.assertEquals(0.0, Vector3.sub(result, expectedResult).norm(), testProperties.DELTA);
    }

    @Test
    public void reflectOnSurfaceTest() {
        Vector3 vector = new Vector3(3.0, 4.0, 5.0);
        Vector3 normalVector = Vector3.X;
        Vector3 expectedResult = new Vector3(-3.0, 4.0, 5.0);

        Vector3 result = VectorUtils.reflectOnSurface(vector, normalVector);

        Assertions.assertEquals(0.0, Vector3.sub(result, expectedResult).norm(), testProperties.DELTA);
    }

    @Test
    public void randomDirectionTest() {
        Vector3 result = VectorUtils.randomDirection();

        Assertions.assertEquals(1.0, result.norm(), testProperties.DELTA);
    }
}
