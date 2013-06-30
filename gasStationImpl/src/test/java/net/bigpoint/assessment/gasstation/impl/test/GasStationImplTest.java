package net.bigpoint.assessment.gasstation.impl.test;

import junit.framework.Assert;
import net.bigpoint.assesment.gasstation.impl.GasStationImpl;
import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * This class contains the unit tests for class GasStationImpl
 * 
 * @author Juan P. Sánchez
 * 
 */
public class GasStationImplTest {

    private GasStationImpl gasStation;

    @Before
    public void setup() {
        gasStation = new GasStationImpl();
    }

    @Test
    public void shouldBeAbleToServeGasSuccessfully() throws Exception {

        // Given
        double amountInLiters = 50d;
        double maxPricePerLiter = 1.5d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters);

        double expectedResult = 75d;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter);

        double actualResult = gasStation.buyGas(GasType.DIESEL, amountInLiters, maxPricePerLiter);

        // Then
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void shouldReturnTheLitersOfGasSoldForEachPump() throws Exception {

        // Given
        double amountInLiters1 = 50d;
        double amountInLiters2 = 60d;
        double maxPricePerLiter1 = 1.5d;
        double maxPricePerLiter2 = 1.6d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters1);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters2);

        double expectedResult1 = 75d;
        double expectedResult2 = 96d;
        double expectedLitersOfGasSold1 = 50d;
        double expectedLitersOfGasSold2 = 60d;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter2);

        double actualResult1 = gasStation.buyGas(GasType.DIESEL, amountInLiters1, maxPricePerLiter1);
        double actualResult2 = gasStation.buyGas(GasType.SUPER, amountInLiters2, maxPricePerLiter2);
        double actualLitersOfGasSold1 = gasStation.getAmountSold(GasType.DIESEL);
        double actualLitersOfGasSold2 = gasStation.getAmountSold(GasType.SUPER);

        // Then
        Assert.assertEquals(expectedResult1, actualResult1);
        Assert.assertEquals(expectedResult2, actualResult2);
        Assert.assertEquals(expectedLitersOfGasSold1, actualLitersOfGasSold1);
        Assert.assertEquals(expectedLitersOfGasSold2, actualLitersOfGasSold2);

    }

    @Test
    public void shouldReturnTheTotalRevenueEarned() throws Exception {

        // Given
        double amountInLiters1 = 50d;
        double amountInLiters2 = 60d;
        double maxPricePerLiter1 = 1.5d;
        double maxPricePerLiter2 = 1.6d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters1);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters2);

        double expectedResult1 = 75d;
        double expectedResult2 = 96d;
        double expectedRevenueEarned = 171d;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter2);

        double actualResult1 = gasStation.buyGas(GasType.DIESEL, amountInLiters1, maxPricePerLiter1);
        double actualResult2 = gasStation.buyGas(GasType.SUPER, amountInLiters2, maxPricePerLiter2);
        double actualRevenueEarned = gasStation.getRevenue();

        // Then
        Assert.assertEquals(expectedResult1, actualResult1);
        Assert.assertEquals(expectedResult2, actualResult2);
        Assert.assertEquals(expectedRevenueEarned, actualRevenueEarned);

    }

    @Test
    public void shouldReturnTheTotalRevenueEarnedByGasType() throws Exception {

        // Given
        double amountInLiters1 = 50d;
        double amountInLiters2 = 60d;
        double amountInLiters3 = 70d;
        double maxPricePerLiter1 = 1.5d;
        double maxPricePerLiter2 = 1.6d;
        double maxPricePerLiter3 = 1.7d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters1);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters2);
        GasPump pump3 = new GasPump(GasType.REGULAR, amountInLiters3);

        double expectedResult1 = 75d;
        double expectedResult2 = 96d;
        double expectedResult3 = 119d;
        double expectedRevenueEarned1 = 75d;
        double expectedRevenueEarned2 = 96d;
        double expectedRevenueEarned3 = 119d;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.addGasPump(pump3);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter2);
        gasStation.setPrice(GasType.REGULAR, maxPricePerLiter3);

        double actualResult1 = gasStation.buyGas(GasType.DIESEL, amountInLiters1, maxPricePerLiter1);
        double actualResult2 = gasStation.buyGas(GasType.SUPER, amountInLiters2, maxPricePerLiter2);
        double actualResult3 = gasStation.buyGas(GasType.REGULAR, amountInLiters3, maxPricePerLiter3);
        double actualRevenueEarned1 = gasStation.getRevenue(GasType.DIESEL);
        double actualRevenueEarned2 = gasStation.getRevenue(GasType.SUPER);
        double actualRevenueEarned3 = gasStation.getRevenue(GasType.REGULAR);

        // Then
        Assert.assertEquals(expectedResult1, actualResult1);
        Assert.assertEquals(expectedResult2, actualResult2);
        Assert.assertEquals(expectedResult3, actualResult3);
        Assert.assertEquals(expectedRevenueEarned1, actualRevenueEarned1);
        Assert.assertEquals(expectedRevenueEarned2, actualRevenueEarned2);
        Assert.assertEquals(expectedRevenueEarned3, actualRevenueEarned3);

    }

    @Test
    public void shouldReturnTheTotalNumberOfSales() throws Exception {

        // Given
        double amountInLiters1 = 50d;
        double amountInLiters2 = 60d;
        double amountInLiters3 = 70d;
        double maxPricePerLiter1 = 1.5d;
        double maxPricePerLiter2 = 1.6d;
        double maxPricePerLiter3 = 1.7d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters1);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters2);
        GasPump pump3 = new GasPump(GasType.REGULAR, amountInLiters3);

        double expectedResult1 = 75d;
        double expectedResult2 = 96d;
        double expectedResult3 = 119d;
        int expectedNumberOfSales = 3;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.addGasPump(pump3);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter2);
        gasStation.setPrice(GasType.REGULAR, maxPricePerLiter3);

        double actualResult1 = gasStation.buyGas(GasType.DIESEL, amountInLiters1, maxPricePerLiter1);
        double actualResult2 = gasStation.buyGas(GasType.SUPER, amountInLiters2, maxPricePerLiter2);
        double actualResult3 = gasStation.buyGas(GasType.REGULAR, amountInLiters3, maxPricePerLiter3);
        int actualNumberOfSales = gasStation.getNumberOfSales();

        // Then
        Assert.assertEquals(expectedResult1, actualResult1);
        Assert.assertEquals(expectedResult2, actualResult2);
        Assert.assertEquals(expectedResult3, actualResult3);
        Assert.assertEquals(expectedNumberOfSales, actualNumberOfSales);

    }

    @Test
    public void shouldReturnTheTotalNumberOfSalesByGasType() throws Exception {

        // Given
        double amountInLiters1 = 50d;
        double amountInLiters2 = 60d;
        double amountInLiters3 = 70d;
        double maxPricePerLiter1 = 1.5d;
        double maxPricePerLiter2 = 1.6d;
        double maxPricePerLiter3 = 1.7d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters1);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters2);
        GasPump pump3 = new GasPump(GasType.REGULAR, amountInLiters3);

        double expectedResult1 = 60d;
        double expectedResult2 = 15d;
        double expectedResult3 = 96d;
        double expectedResult4 = 119d;
        int expectedNumberOfSales1 = 2;
        int expectedNumberOfSales2 = 1;
        int expectedNumberOfSales3 = 1;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.addGasPump(pump3);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter2);
        gasStation.setPrice(GasType.REGULAR, maxPricePerLiter3);

        double actualResult1 = gasStation.buyGas(GasType.DIESEL, amountInLiters1 - 10d, maxPricePerLiter1);
        double actualResult2 = gasStation.buyGas(GasType.DIESEL, 10d, maxPricePerLiter1);
        double actualResult3 = gasStation.buyGas(GasType.SUPER, amountInLiters2, maxPricePerLiter2);
        double actualResult4 = gasStation.buyGas(GasType.REGULAR, amountInLiters3, maxPricePerLiter3);
        int actualNumberOfSales1 = gasStation.getNumberOfSales(GasType.DIESEL);
        int actualNumberOfSales2 = gasStation.getNumberOfSales(GasType.SUPER);
        int actualNumberOfSales3 = gasStation.getNumberOfSales(GasType.REGULAR);

        // Then
        Assert.assertEquals(expectedResult1, actualResult1);
        Assert.assertEquals(expectedResult2, actualResult2);
        Assert.assertEquals(expectedResult3, actualResult3);
        Assert.assertEquals(expectedResult4, actualResult4);
        Assert.assertEquals(expectedNumberOfSales1, actualNumberOfSales1);
        Assert.assertEquals(expectedNumberOfSales2, actualNumberOfSales2);
        Assert.assertEquals(expectedNumberOfSales3, actualNumberOfSales3);

    }

    @Test(expected = GasTooExpensiveException.class)
    public void shouldThrowANewGasTooExpensiveExceptionIfTheGasIsTooPricey() throws Exception {

        // Given
        double amountInLiters = 50d;
        double maxPricePerLiter = 1.5d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters);

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter + 0.1);

        gasStation.buyGas(GasType.DIESEL, amountInLiters, maxPricePerLiter);

        // Then

        // Exception !!!

    }

    @Test(expected = NotEnoughGasException.class)
    public void shouldThrowANewNotEnoughGasExceptionIfThereIsNoEnoughGas() throws Exception {

        // Given
        double amountInLiters = 50d;
        double maxPricePerLiter = 1.5d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters - 2d);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters);

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter);

        gasStation.buyGas(GasType.DIESEL, amountInLiters, maxPricePerLiter);

        // Then

        // Exception !!!

    }
}
