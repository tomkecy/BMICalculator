package cybulski.tomasz.tomaszcybulskilab1;

/**
 * Created by tomcy on 20.03.2017.
 */

public class CountBMIMetric implements ICountBMI {
    static final float MINIMAL_HEIGHT = 0.5f;
    static final float MAXIMAL_HEIGHT = 2.5f;
    static final float MINIMAL_MASS = 10f;
    static final float MAXIMAL_MASS = 250f;

    @Override
    public boolean isValidMass(float mass) {
        return MINIMAL_MASS <= mass && mass <= MAXIMAL_MASS;
    }

    @Override
    public boolean isValidHeight(float height) {
        return MINIMAL_HEIGHT <= height && height <= MAXIMAL_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) throws InvalidMassOrHeightException{
        if (!isValidHeight(height) || !isValidMass(mass)){
            throw new InvalidMassOrHeightException();
        }
        return mass/(height*height);
    }
}