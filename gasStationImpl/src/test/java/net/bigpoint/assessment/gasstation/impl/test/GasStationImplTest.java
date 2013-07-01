package net.bigpoint.assessment.gasstation.impl.test;

import java.util.concurrent.CyclicBarrier;

import junit.framework.Assert;
import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;
import net.bigpoint.assessment.gasstation.impl.GasStationImpl;
import net.bigpoint.assessment.gasstation.utils.GasStationUtils;

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
    public void shouldReturnTheGasPrices() throws Exception {

        // Given
        double maxPricePerLiter1 = 1.5d;
        double maxPricePerLiter2 = 1.6d;
        double maxPricePerLiter3 = 1.7d;

        // When
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter2);
        gasStation.setPrice(GasType.REGULAR, maxPricePerLiter3);

        double expectedGasPrice1 = gasStation.getPrice(GasType.DIESEL);
        double expectedGasPrice2 = gasStation.getPrice(GasType.SUPER);
        double expectedGasPrice3 = gasStation.getPrice(GasType.REGULAR);

        // Then
        Assert.assertEquals(expectedGasPrice1, maxPricePerLiter1);
        Assert.assertEquals(expectedGasPrice2, maxPricePerLiter2);
        Assert.assertEquals(expectedGasPrice3, maxPricePerLiter3);
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

    @Test
    public void shouldReturnTheNumberOfCancelledSalesWhenTheGasIsTooPricey() throws Exception {

        // Given
        double amountInLiters = 50d;
        double maxPricePerLiter = 1.5d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters);

        int expectedNumberOfCancelledSales = 2;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter + 0.1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter + 0.2);

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters, maxPricePerLiter);
        } catch (GasTooExpensiveException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.SUPER, amountInLiters, maxPricePerLiter);
        } catch (GasTooExpensiveException e) {
            // Don't do anything
        }

        int actualNumberOfCancelledSales = gasStation.getNumberOfCancellationsTooExpensive();

        // Then
        Assert.assertEquals(expectedNumberOfCancelledSales, actualNumberOfCancelledSales);

    }

    @Test
    public void shouldReturnTheNumberOfCancelledSalesWhenTheGasIsTooPriceyByGasType() throws Exception {

        // Given
        double amountInLiters = 50d;
        double maxPricePerLiter = 1.5d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters);

        int expectedNumberOfCancelledSales1 = 3;
        int expectedNumberOfCancelledSales2 = 1;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter + 0.1);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter + 0.2);

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters, maxPricePerLiter);
        } catch (GasTooExpensiveException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters, maxPricePerLiter);
        } catch (GasTooExpensiveException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters, maxPricePerLiter);
        } catch (GasTooExpensiveException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.SUPER, amountInLiters, maxPricePerLiter);
        } catch (GasTooExpensiveException e) {
            // Don't do anything
        }

        int actualNumberOfCancelledSales1 = gasStation.getNumberOfCancellationsTooExpensive(GasType.DIESEL);
        int actualNumberOfCancelledSales2 = gasStation.getNumberOfCancellationsTooExpensive(GasType.SUPER);

        // Then
        Assert.assertEquals(expectedNumberOfCancelledSales1, actualNumberOfCancelledSales1);
        Assert.assertEquals(expectedNumberOfCancelledSales2, actualNumberOfCancelledSales2);

    }

    @Test
    public void shouldReturnTheNumberOfCancelledSalesWhenThereIsNoEnoughGas() throws Exception {

        // Given
        double amountInLiters = 50d;
        double maxPricePerLiter = 1.5d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters);

        int expectedNumberOfCancelledSales = 2;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter);

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters + 10, maxPricePerLiter);
        } catch (NotEnoughGasException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.SUPER, amountInLiters + 10, maxPricePerLiter);
        } catch (NotEnoughGasException e) {
            // Don't do anything
        }

        int actualNumberOfCancelledSales = gasStation.getNumberOfCancellationsNoGas();

        // Then
        Assert.assertEquals(expectedNumberOfCancelledSales, actualNumberOfCancelledSales);

    }

    @Test
    public void shouldReturnTheNumberOfCancelledSalesWhenThereIsNoEnoughGasByGasType() throws Exception {

        // Given
        double amountInLiters = 50d;
        double maxPricePerLiter = 1.5d;

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLiters);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLiters);

        int expectedNumberOfCancelledSales1 = 3;
        int expectedNumberOfCancelledSales2 = 1;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiter);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiter);

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters + 10, maxPricePerLiter);
        } catch (NotEnoughGasException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters + 10, maxPricePerLiter);
        } catch (NotEnoughGasException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.DIESEL, amountInLiters + 10, maxPricePerLiter);
        } catch (NotEnoughGasException e) {
            // Don't do anything
        }

        try {
            gasStation.buyGas(GasType.SUPER, amountInLiters + 10, maxPricePerLiter);
        } catch (NotEnoughGasException e) {
            // Don't do anything
        }

        int actualNumberOfCancelledSales1 = gasStation.getNumberOfCancellationsNoGas(GasType.DIESEL);
        int actualNumberOfCancelledSales2 = gasStation.getNumberOfCancellationsNoGas(GasType.SUPER);

        // Then
        Assert.assertEquals(expectedNumberOfCancelledSales1, actualNumberOfCancelledSales1);
        Assert.assertEquals(expectedNumberOfCancelledSales2, actualNumberOfCancelledSales2);

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

    @Test
    public void shouldPreserveTheCorrectDataOnSharedVariablesNoMatterTheActualThreadExecution() throws Exception {

        // We are talking about variables that will have the same value in the end no matter the execution

        // Given
        double amountInLitersDiesel = 50d;
        double amountInLitersSuper = 50d;
        double amountInLitersRegular = 50d;
        double maxPricePerLiterDiesel = 1.3d;
        double maxPricePerLiterSuper = 1.4d;
        double maxPricePerLiterRegular = 1.5d;

        final CyclicBarrier gate = new CyclicBarrier(14); // make the threads start at unison using a barrier gate

        GasStationUtils.createAndActivateUser(gasStation, gate, "Anne", 10d, 1.5, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Jimmy", 10d, 1.5, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Howard", 25d, 1.5, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Kim", 5d, 1.5, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Harold", 51d, 1.5, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Bob", 24d, 1.5, GasType.SUPER);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Rose", 20d, 1.5, GasType.SUPER);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Peter", 6d, 1.5, GasType.SUPER);
        GasStationUtils.createAndActivateUser(gasStation, gate, "George", 30d, 1.2, GasType.SUPER);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Jill", 7d, 1.5, GasType.REGULAR);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Kat", 28d, 1.5, GasType.REGULAR);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Lars", 15d, 1.5, GasType.REGULAR);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Harry", 62d, 1.5, GasType.REGULAR);

        GasPump pump1 = new GasPump(GasType.DIESEL, amountInLitersDiesel);
        GasPump pump2 = new GasPump(GasType.SUPER, amountInLitersSuper);
        GasPump pump3 = new GasPump(GasType.REGULAR, amountInLitersRegular);

        double expectedLitersOfGasSoldDiesel = 50d;
        double expectedLitersOfGasSoldSuper = 50d;
        double expectedLitersOfGasSoldRegular = 50d;

        double expectedRevenueEarned = 210d;
        double expectedRevenueEarnedDiesel = 65d;
        double expectedRevenueEarnedSuper = 70d;
        double expectedRevenueEarnedRegular = 75d;

        int expectedNumberOfSuccessfulSales = 10;
        int expectedNumberOfSuccessfulSalesDiesel = 4;
        int expectedNumberOfSuccessfulSalesSuper = 3;
        int expectedNumberOfSuccessfulSalesRegular = 3;

        int expectedCancellationsNoGas = 2;
        int expectedCancellationsNoGasDiesel = 1;
        int expectedCancellationsNoGasSuper = 0;
        int expectedCancellationsNoGasRegular = 1;

        int expectedCancellationsTooExpensive = 1;
        int expectedCancellationsTooExpensiveDiesel = 0;
        int expectedCancellationsTooExpensiveSuper = 1;
        int expectedCancellationsTooExpensiveRegular = 0;

        // When
        gasStation.addGasPump(pump1);
        gasStation.addGasPump(pump2);
        gasStation.addGasPump(pump3);
        gasStation.setPrice(GasType.DIESEL, maxPricePerLiterDiesel);
        gasStation.setPrice(GasType.SUPER, maxPricePerLiterSuper);
        gasStation.setPrice(GasType.REGULAR, maxPricePerLiterRegular);
        gate.await(); // go!

        Thread.sleep(30000); // wait for the people to fill in the gas!

        double actualLitersOfGasSoldDiesel = gasStation.getAmountSold(GasType.DIESEL);
        double actualLitersOfGasSoldSuper = gasStation.getAmountSold(GasType.SUPER);
        double actualLitersOfGasSoldRegular = gasStation.getAmountSold(GasType.REGULAR);

        double actualRevenueEarned = gasStation.getRevenue();
        double actualRevenueEarnedDiesel = gasStation.getRevenue(GasType.DIESEL);
        double actualRevenueEarnedSuper = gasStation.getRevenue(GasType.SUPER);
        double actualRevenueEarnedRegular = gasStation.getRevenue(GasType.REGULAR);

        int actualNumberOfSuccessfulSales = gasStation.getNumberOfSales();
        int actualNumberOfSuccessfulSalesDiesel = gasStation.getNumberOfSales(GasType.DIESEL);
        int actualNumberOfSuccessfulSalesSuper = gasStation.getNumberOfSales(GasType.SUPER);
        int actualNumberOfSuccessfulSalesRegular = gasStation.getNumberOfSales(GasType.REGULAR);

        int actualCancellationsNoGas = gasStation.getNumberOfCancellationsNoGas();
        int actualCancellationsNoGasDiesel = gasStation.getNumberOfCancellationsNoGas(GasType.DIESEL);
        int actualCancellationsNoGasSuper = gasStation.getNumberOfCancellationsNoGas(GasType.SUPER);
        int actualCancellationsNoGasRegular = gasStation.getNumberOfCancellationsNoGas(GasType.REGULAR);

        int actualCancellationsTooExpensive = gasStation.getNumberOfCancellationsTooExpensive();
        int actualCancellationsTooExpensiveDiesel = gasStation.getNumberOfCancellationsTooExpensive(GasType.DIESEL);
        int actualCancellationsTooExpensiveSuper = gasStation.getNumberOfCancellationsTooExpensive(GasType.SUPER);
        int actualCancellationsTooExpensiveRegular = gasStation.getNumberOfCancellationsTooExpensive(GasType.REGULAR);

        // Then
        Assert.assertEquals(expectedLitersOfGasSoldDiesel, actualLitersOfGasSoldDiesel);
        Assert.assertEquals(expectedLitersOfGasSoldSuper, actualLitersOfGasSoldSuper);
        Assert.assertEquals(expectedLitersOfGasSoldRegular, actualLitersOfGasSoldRegular);

        Assert.assertEquals(expectedRevenueEarned, actualRevenueEarned);
        Assert.assertEquals(expectedRevenueEarnedDiesel, actualRevenueEarnedDiesel);
        Assert.assertEquals(expectedRevenueEarnedSuper, actualRevenueEarnedSuper);
        Assert.assertEquals(expectedRevenueEarnedRegular, actualRevenueEarnedRegular);

        Assert.assertEquals(expectedNumberOfSuccessfulSales, actualNumberOfSuccessfulSales);
        Assert.assertEquals(expectedNumberOfSuccessfulSalesDiesel, actualNumberOfSuccessfulSalesDiesel);
        Assert.assertEquals(expectedNumberOfSuccessfulSalesSuper, actualNumberOfSuccessfulSalesSuper);
        Assert.assertEquals(expectedNumberOfSuccessfulSalesRegular, actualNumberOfSuccessfulSalesRegular);

        Assert.assertEquals(expectedCancellationsNoGas, actualCancellationsNoGas);
        Assert.assertEquals(expectedCancellationsNoGasDiesel, actualCancellationsNoGasDiesel);
        Assert.assertEquals(expectedCancellationsNoGasSuper, actualCancellationsNoGasSuper);
        Assert.assertEquals(expectedCancellationsNoGasRegular, actualCancellationsNoGasRegular);

        Assert.assertEquals(actualCancellationsTooExpensive, expectedCancellationsTooExpensive);
        Assert.assertEquals(actualCancellationsTooExpensiveDiesel, expectedCancellationsTooExpensiveDiesel);
        Assert.assertEquals(actualCancellationsTooExpensiveSuper, expectedCancellationsTooExpensiveSuper);
        Assert.assertEquals(actualCancellationsTooExpensiveRegular, expectedCancellationsTooExpensiveRegular);

    }
}
