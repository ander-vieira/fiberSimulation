package com.fibersim.core.utils;

import com.fibersim.core.model.common.Vector3;

public abstract class VectorUtils {

    public static Vector3 projectOnVector(Vector3 vector, Vector3 projectVector) {
        return projectVector.unit().scale(Vector3.dot(vector, projectVector));
    }

    public static Vector3 projectOnSurface(Vector3 vector, Vector3 normalVector) {
        return Vector3.sub(vector, projectOnVector(vector, normalVector));
    }

    public static Vector3 reflectOnSurface(Vector3 vector, Vector3 normalVector) {
        return Vector3.sub(vector, projectOnVector(vector, normalVector).scale(2));
    }

    public static Vector3 randomDirection() {
        double theta = 2*Math.PI*Math.random();
        double cosTheta = Math.cos(theta), sinTheta = Math.sqrt(1-cosTheta*cosTheta);
        double z = 2*Math.random()-1, xy = Math.sqrt(1-z*z);

        return new Vector3(cosTheta*xy, sinTheta*xy, z);
    }
}
