package com.fibersim.core.utils;

import com.fibersim.core.TestProperties;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MathUtilsTest {
    @Autowired
    TestProperties testProperties;

    @Test
    public void fresnelRTest() {
        Assertions.assertEquals(0, MathUtils.fresnelR(1, 1, 1, 1), testProperties.DELTA);
        Assertions.assertEquals(0.04, MathUtils.fresnelR(1.5, 1, 1, 1), testProperties.DELTA);
    }

    @Test
    public void randomExponentialTest() {
        Assertions.assertEquals(Double.POSITIVE_INFINITY, MathUtils.randomExponential(-1));
        Assertions.assertTrue(MathUtils.randomExponential(1) >= 0);
    }
}
