package com.fibersim.core.utils;

public abstract class MathUtils {
    public static final double h = 6.62607015e-34;
    public static final double c = 2.99792458e8;

    public static double fresnelR(double N_I, double N_T, double cos_I, double cos_T) {
        return (Math.pow((N_I*cos_I-N_T*cos_T)/(N_I*cos_I+N_T*cos_T), 2)+Math.pow((N_T*cos_I-N_I*cos_T)/(N_T*cos_I+N_I*cos_T), 2))/2;
    }

    public static double randomExponential(double alpha) {
        if(alpha <= 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return -Math.log(Math.random())/alpha;
        }
    }
}
