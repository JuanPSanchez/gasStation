package net.bigpont.assesment.gasstation.utils.test;

import java.util.concurrent.CyclicBarrier;

import junit.framework.Assert;
import net.bigpoint.assesment.gasstation.utils.User;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * This class contains the unit tests for class UserTest
 * 
 * @author Juan P. Sánchez
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    @Mock
    private GasStation gasStationMock;

    private User user;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(UserTest.class);
        this.user = new User();

    }

    @Test
    public void shouldBeAbleToGetSomeGas() throws Exception {

        // Given
        GasType gasType = GasType.SUPER;
        double amountInLiters = 30d;
        double maxPricePerLiter = 1.5d;

        double expectedAmountOfMoneyToPay = 45d;

        // When
        Mockito.when(gasStationMock.buyGas(gasType, amountInLiters, maxPricePerLiter)).thenReturn(45d);

        this.user.setUserName("Jack");
        this.user.setTypeOfGasNeeded(gasType);
        this.user.setGasNeeded(amountInLiters);
        this.user.setMaxMoneyPerLiter(maxPricePerLiter);
        this.user.setGasStation(gasStationMock);
        this.user.setGate(new CyclicBarrier(1));

        Thread userThread = new Thread(user);
        userThread.start();

        Thread.sleep(5000); // Wait for the user to fill her up

        // Then
        Mockito.verify(gasStationMock, Mockito.times(1)).buyGas(gasType, amountInLiters, maxPricePerLiter);
        Assert.assertEquals(expectedAmountOfMoneyToPay, this.user.getAmountOfMoneyToPay());

    }

    @Test
    public void shouldNotFillUpGasIfItIsTooExpensive() throws Exception {

        // Given
        GasType gasType = GasType.SUPER;
        double amountInLiters = 30d;
        double maxPricePerLiter = 1.5d;

        double expectedAmountOfMoneyToPay = 0d;

        // When
        Mockito.when(gasStationMock.buyGas(gasType, amountInLiters, maxPricePerLiter)).thenThrow(
                new GasTooExpensiveException());

        this.user.setUserName("Aaron");
        this.user.setTypeOfGasNeeded(gasType);
        this.user.setGasNeeded(amountInLiters);
        this.user.setMaxMoneyPerLiter(maxPricePerLiter);
        this.user.setGasStation(gasStationMock);
        this.user.setGate(new CyclicBarrier(1));

        Thread userThread = new Thread(user);
        userThread.start();

        Thread.sleep(5000); // Wait for the user to fill her up

        // Then
        Mockito.verify(gasStationMock, Mockito.times(1)).buyGas(gasType, amountInLiters, maxPricePerLiter);
        Assert.assertEquals(expectedAmountOfMoneyToPay, this.user.getAmountOfMoneyToPay());

    }

    @Test
    public void shouldNotFillUpGasIfThereIsNotEnoughAvailable() throws Exception {

        // Given
        GasType gasType = GasType.SUPER;
        double amountInLiters = 30d;
        double maxPricePerLiter = 1.5d;

        double expectedAmountOfMoneyToPay = 0d;

        // When
        Mockito.when(gasStationMock.buyGas(gasType, amountInLiters, maxPricePerLiter)).thenThrow(
                new NotEnoughGasException());

        this.user.setUserName("Christine");
        this.user.setTypeOfGasNeeded(gasType);
        this.user.setGasNeeded(amountInLiters);
        this.user.setMaxMoneyPerLiter(maxPricePerLiter);
        this.user.setGasStation(gasStationMock);
        this.user.setGate(new CyclicBarrier(1));

        Thread userThread = new Thread(user);
        userThread.start();

        Thread.sleep(5000); // Wait for the user to fill her up

        // Then
        Mockito.verify(gasStationMock, Mockito.times(1)).buyGas(gasType, amountInLiters, maxPricePerLiter);
        Assert.assertEquals(expectedAmountOfMoneyToPay, this.user.getAmountOfMoneyToPay());

    }

    @Test
    public void shouldThrowAnExceptionBeAbleToCopeWithAGasRequestWhenThereIsNoGasStationToBeFound() throws Exception {

        // Given
        GasType gasType = GasType.SUPER;
        double amountInLiters = 30d;
        double maxPricePerLiter = 1.5d;

        double expectedAmountOfMoneyToPay = 0d;

        // When
        this.user.setUserName("Samantha");
        this.user.setTypeOfGasNeeded(gasType);
        this.user.setGasNeeded(amountInLiters);
        this.user.setMaxMoneyPerLiter(maxPricePerLiter);
        this.user.setGasStation(null);
        this.user.setGate(new CyclicBarrier(1));

        Thread userThread = new Thread(user);
        userThread.start();

        Thread.sleep(5000); // Wait for the user to fill her up

        // Then Jack:
        Mockito.verify(gasStationMock, Mockito.times(0)).buyGas(gasType, amountInLiters, maxPricePerLiter);
        Assert.assertEquals(expectedAmountOfMoneyToPay, this.user.getAmountOfMoneyToPay());

    }

    @Test
    public void SettersAndGettersShouldWorkAsIntended() throws Exception {

        // Given
        GasType typeOfGasNeeded = GasType.REGULAR;
        Double amountOfGasNeeded = 40d; // fill her up
        Double maxMoneyPerLiter = 1.5d; // reasonable?
        String userName = "Joe";
        CyclicBarrier gate = new CyclicBarrier(1);
        Double amountOfMoneyToPay = 0.0;

        // When
        user.setTypeOfGasNeeded(typeOfGasNeeded);
        user.setGasNeeded(amountOfGasNeeded);
        user.setMaxMoneyPerLiter(maxMoneyPerLiter);
        user.setUserName(userName);
        user.setGate(gate);
        user.setGasStation(gasStationMock);

        // Then
        Assert.assertEquals(typeOfGasNeeded, user.getTypeOfGasNeeded());
        Assert.assertEquals(amountOfGasNeeded, user.getGasNeeded());
        Assert.assertEquals(maxMoneyPerLiter, user.getMaxMoneyPerLiter());
        Assert.assertEquals(userName, user.getUserName());
        Assert.assertEquals(gate, user.getGate());
        Assert.assertEquals(amountOfMoneyToPay, user.getAmountOfMoneyToPay());
        Assert.assertEquals(gasStationMock, user.getGasStation());

    }
}
