package com.fibersim.core.model.element;

import com.fibersim.core.data.medium.Medium;
import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.common.Vector3;
import com.fibersim.core.model.condition.Condition;
import com.fibersim.core.model.interphase.Interphase;
import com.fibersim.core.utils.VectorUtils;
import com.fibersim.server.util.MathUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefractorElement implements Element {
    private final Interphase interphase;
    private final Condition condition;
    private final Medium mediumPlus;
    private final Medium mediumMinus;

    @Override
    public double intersect(Ray ray) {
        return interphase.intersect(ray);
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

        Vector3 reflectedDirection = VectorUtils.reflectOnSurface(ray.getVel(), normalVector);

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
                Vector3 tangentVector = VectorUtils.projectOnSurface(ray.getVel(), normalVector);
                Vector3 newTangentVector = tangentVector.scale(N_I/N_T);
                Vector3 newNormalVector = normalVector.scale(cosT);
                Vector3 refractedDirection = Vector3.add(newTangentVector, newNormalVector);

                ray.setVel(refractedDirection.unit());
            }
        }
    }
}
