package cybulski.tomasz.tomaszcybulskilab1;

import org.junit.Test;

import cybulski.tomasz.tomaszcybulskilab1.Abstract.ICountBMI;
import cybulski.tomasz.tomaszcybulskilab1.Concrete.CountBMIMetric;
import cybulski.tomasz.tomaszcybulskilab1.Entities.InvalidMassOrHeightException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by tomcy on 20.03.2017.
 */

public class CountBMITests {
    @Test
    public void massUnderZero_isInvalid(){
        //GIVEN
        float testMass = -1.0f;
        //WHERE
        mass_isInvalid(testMass);
    }

    private void mass_isInvalid(float testMass) {
        ICountBMI countBMI = new CountBMIMetric();
        //THEN
        boolean actual = countBMI.isValidMass(testMass);

        assertFalse(actual);
    }

    @Test
    public void zeroMass_isInvalid(){
        //GIVEN
        float testMass = 0f;
        //WHERE
        mass_isInvalid(testMass);
    }

    @Test
    public void massUnderMinimal_isInvalid(){
        //GIVEN
        float testMass = 5f;

        //WHERE
        mass_isInvalid(testMass);
    }

    @Test
    public void massOverMaximal_isInvalid(){
        //GIVEN
        float testMass = 300f;
        //WHERE...THEN
        mass_isInvalid(testMass);
    }

    @Test
    public void minimalMass_isValid(){
        //GIVEN
        float testMass = 10f;
        //WHERE ... THEN
        mass_isValid(testMass);
    }

    @Test
    public void validMass_isValid(){
        //GIVEN
        float testMass = 100f;

        //WHERE...THEN
        mass_isValid(testMass);
    }

    @Test
    public void maximalMass_isValid(){
        //GIVEN
        float testMass = 250;
        //WHERE ... THEN
        mass_isValid(testMass);
    }

    private void mass_isValid(float testMass) {
        //WHERE
        ICountBMI countBMI = new CountBMIMetric();
        //THEN
        boolean actual = countBMI.isValidMass(testMass);

        assertTrue(actual);
    }


    //sprawdzic wartości ujemne, brzegowe,
    @Test
    public void heightUnderZero_isInvalid(){
        //GIVEN
        float testHeight = -1.0f;
        //WHERE
        height_isInvalid(testHeight);
    }

    private void height_isInvalid(float testHeight) {
        //WHERE
        ICountBMI countBMI = new CountBMIMetric();
        //THEN
        boolean actual = countBMI.isValidHeight(testHeight);

        assertFalse(actual);
    }

    @Test
    public void zeroHeight_isInvalid(){
        //GIVEN
        float testHeight = 0f;
        //WHERE..THEN
        height_isInvalid(testHeight);
    }

    @Test
    public void heightUnderMinimal_isInvalid(){
        //GIVEN
        float testHeight = 0.45f;
        //WHERE
        height_isInvalid(testHeight);
    }

    @Test
    public void heightOverMaximal_isInvalid(){
        //GIVEN
        float testHeight = 2.6f;
        //WHERE..THEN
        height_isInvalid(testHeight);
    }

    @Test
    public void minimalHeight_isValid(){
        //GIVEN
        float testHeight = 0.5f;
        //WHERE ... THEN
        height_isValid(testHeight);
    }

    @Test
    public void validHeight_isValid(){
        //GIVEN
        float testHeight = 1.75f;

        //WHERE...THEN
        height_isValid(testHeight);
    }

    @Test
    public void maximalHeight_isValid(){
        //GIVEN
        float testHeight = 2.5f;
        //WHERE ... THEN
        height_isValid(testHeight);
    }

    private void height_isValid(float testHeight) {
        //WHERE
        ICountBMI countBMI = new CountBMIMetric();
        //THEN
        boolean actual = countBMI.isValidHeight(testHeight);

        assertTrue(actual);
    }

    @Test
    public void countedBMI_isValid(){
        //GIVEN
        float testMass = 70f;
        float testHeight = 1.75f;

        //WHERE
        ICountBMI countBMI = new CountBMIMetric();

        //THEN
        float actual = countBMI.countBMI(testMass, testHeight);
        float expected = testMass/(testHeight * testHeight);
        assertEquals(actual, expected);
    }

    @Test(expected = InvalidMassOrHeightException.class )
    public void invalidMassCountedBMI_isInvalid(){
        //GIVEN
        float testMass = 300f;
        float testHeight = 1.75f;
        //WHERE
        ICountBMI countBMI = new CountBMIMetric();
        //THEN
        float actual = countBMI.countBMI(testMass, testHeight);
    }

    @Test(expected = InvalidMassOrHeightException.class)
    public void invalidHeightCountedBMI_isInvalid(){
        //GIVEN
        float testMass = 70f;
        float testHeight = 3.0f;

        //WHERE
        ICountBMI countBMI = new CountBMIMetric();

        //THEN
        float actual = countBMI.countBMI(testMass, testHeight);

    }
}
