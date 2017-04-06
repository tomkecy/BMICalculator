package cybulski.tomasz.tomaszcybulskilab1.Abstract;

import cybulski.tomasz.tomaszcybulskilab1.Entities.InvalidMassOrHeightException;

/**
 * Created by tomcy on 20.03.2017.
 */

public interface ICountBMI {
    boolean isValidMass(float mass);
    boolean isValidHeight(float height);
    float countBMI(float mass, float height) throws InvalidMassOrHeightException;
}
