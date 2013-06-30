package net.bigpoint.assessment.gasstation.test;

import junit.framework.Assert;
import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasType;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * This class contains the unit tests for class GasPumpTest
 * 
 * @author Juan P. Sánchez
 * 
 */
public class GasPumpTest {

    private static final double AVAILABLE_GAS = 50d;
    private static final GasType GAS_TYPE = GasType.DIESEL;

    private GasPump gasPump;

    @Before
    public void setup() {
        gasPump = new GasPump(GAS_TYPE, AVAILABLE_GAS);
    }

    @Test
    public void shouldPumpGasAndHaveTheCorrectRemainingAmountAfterwards() throws Exception {

        // Given
        Double expectedAmountAfterPump = 10d;

        // When
        gasPump.pumpGas(40d);

        // Then
        Assert.assertEquals(expectedAmountAfterPump, gasPump.getRemainingAmount());

    }

    @Test
    public void shouldReturnTheGasTypeThePumpServes() throws Exception {

        // When
        GasType result = gasPump.getGasType();

        // Then
        Assert.assertEquals(GAS_TYPE, result);

    }

}
