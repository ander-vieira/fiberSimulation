package com.fibersim.core.raytracing.element;

import com.fibersim.core.resources.medium.Medium;
import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;
import com.fibersim.core.raytracing.condition.Condition;
import com.fibersim.core.raytracing.interphase.Interphase;
import com.fibersim.core.utils.MathUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefractorElement implements Element {
    private final Interphase interphase;
    private final Condition condition;
    private final Medium mediumPlus;
    private final Medium mediumMinus;

    @Override
    public double intersect(Ray ray, double limit) {
        return MathUtils.getMinimumValue(interphase.intersect(ray), limit);
    }

    @Override
    public void process(Ray ray) {
        if(!condition.check(ray)) {
            return;
        }

        Vector3 normalVector = interphase.getNormalVector(ray.getPos());

        double cosI = Vector3.dot(ray.getVel(), normalVector);
        double N_I, N_T;

        if(cosI > 0) {
            N_I = this.mediumMinus.getN().evaluate(ray.getWavelength());
            N_T = this.mediumPlus.getN().evaluate(ray.getWavelength());
        } else {
            N_I = this.mediumPlus.getN().evaluate(ray.getWavelength());
            N_T = this.mediumMinus.getN().evaluate(ray.getWavelength());
        }

        double sinI = Math.sqrt(1-cosI*cosI);
        double sinT = sinI*N_I/N_T;

        Vector3 reflectedDirection = Vector3.reflectOnSurface(ray.getVel(), normalVector);

        if(sinT > 1) {
            //Total internal reflection
            ray.setVel(reflectedDirection);
        } else {
            double cosT = Math.copySign(Math.sqrt(1-sinT*sinT), cosI);
            double R = MathUtils.fresnelR(N_I, N_T, cosI, cosT);

            if(Math.random() < R) {
                //Partial reflection
                ray.setVel(reflectedDirection);
            } else {
                //Refraction
                Vector3 tangentVector = Vector3.projectOnSurface(ray.getVel(), normalVector);
                Vector3 newTangentVector = tangentVector.scale(N_I/N_T);
                Vector3 newNormalVector = normalVector.scale(cosT);
                Vector3 refractedDirection = Vector3.add(newTangentVector, newNormalVector);

                ray.setVel(refractedDirection.unit());
            }
        }
    }
}
