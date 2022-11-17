package com.fibersim.core.model.interphase;

import com.fibersim.core.utils.VectorUtils;
import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.common.Vector3;

public class CylinderInterphase implements Interphase {
    private final Vector3 origin;
    private final Vector3 axis;
    private final double R2;

    public CylinderInterphase(Vector3 origin, Vector3 axis, double R) {
        this.origin = origin;
        this.axis = axis.unit();
        this.R2 = R*R;
    }

    @Override
    public double intersect(Ray ray) {
        Vector3 relativePos = Vector3.sub(ray.getPos(), this.origin);
        Vector3 vel = ray.getVel();

        double velAxis = Vector3.dot(vel, this.axis);
        double a = vel.norm2()-velAxis*velAxis;

        if(a == 0) {
            //Ray is parallel to cylinder axis -> no intersection
            return Double.POSITIVE_INFINITY;
        }

        double posAxis = Vector3.dot(relativePos, this.axis);
        double B = (Vector3.dot(relativePos, vel)-posAxis*velAxis)/a;
        double C = (relativePos.norm2()-posAxis*posAxis-R2)/a;
        double determinant = B*B-C;

        if(determinant <= 0) {
            //All roots are imaginary -> ray doesn't intersect cylinder
            return Double.POSITIVE_INFINITY;
        }

        if(C < 0) {
            //Ray is inside the fiber -> pick second intersection
            return -B+Math.sqrt(determinant);
        } else {
            //Ray is outside the fiber
            if(B < 0) {
                //Ray is heading toward the fiber -> pick first intersection
                return -B-Math.sqrt(determinant);
            } else {
                //Ray is heading away from the fiber -> no intersection
                return Double.POSITIVE_INFINITY;
            }
        }
    }

    @Override
    public Vector3 getNormalVector(Vector3 pos) {
        Vector3 relativePos = Vector3.sub(pos, this.origin);

        Vector3 cylinderPos = VectorUtils.projectOnSurface(relativePos, this.axis);

        return cylinderPos.unit();
    }
}
