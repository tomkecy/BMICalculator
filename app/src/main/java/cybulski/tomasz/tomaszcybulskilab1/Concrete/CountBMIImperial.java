package cybulski.tomasz.tomaszcybulskilab1.Concrete;

import cybulski.tomasz.tomaszcybulskilab1.Abstract.ICountBMI;
import cybulski.tomasz.tomaszcybulskilab1.Entities.InvalidMassOrHeightException;

/**
 * Created by tomcy on 02.04.2017.
 */

public class CountBMIImperial implements ICountBMI {
    private static final float MINIMAL_HEIGHT = 19.6850394f;
    private static final float MAXIMAL_HEIGHT = 98.4251969f;
    private static final float MINIMAL_MASS = 22.0462262f;
    private static final float MAXIMAL_MASS = 551.155655f;

    @Override
    public boolean isValidMass(float mass) {
        return MINIMAL_MASS <= mass && mass <= MAXIMAL_MASS;
    }

    @Override
    public boolean isValidHeight(float height) {
        return MINIMAL_HEIGHT <= height && height <= MAXIMAL_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) throws InvalidMassOrHeightException {
        if (!isValidHeight(height) || !isValidMass(mass)){
            throw new InvalidMassOrHeightException();
        }
        return (mass/(height*height))*703;
    }
}
