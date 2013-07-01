package net.bigpoint.assesment.gasstation.utils;

import java.text.MessageFormat;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exception.ThereIsNoGasStationHereException;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class models an user of the gas stations that pulls over to fill up some gas
 * 
 * @author Juan P. Sánchez
 * 
 */
public class User implements Runnable {

    private GasStation gasStation;

    private GasType typeOfGasNeeded = GasType.REGULAR;
    private Double amountOfGasNeeded = 40d; // fill her up
    private Double maxMoneyPerLiter = 1.5d; // reasonable?
    private String userName = "Joe";

    private CyclicBarrier gate;

    private Double amountOfMoneyToPay = 0.0;

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Override
    public void run() {

        try {
            gate.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (BrokenBarrierException e1) {
            e1.printStackTrace();
        }

        String message = MessageFormat.format(
                "{0}: Trying to fill some gas up. I need {1} liters of {2} and I will pay {3} euros max.", userName,
                amountOfGasNeeded, typeOfGasNeeded, maxMoneyPerLiter);
        System.out.println(message);
        logger.info(message);

        try {
            amountOfMoneyToPay = getSomeGas(gasStation, typeOfGasNeeded, amountOfGasNeeded, maxMoneyPerLiter);
        } catch (GasTooExpensiveException e) {
            message = MessageFormat.format("{0}: Oops, {1} gas is too expensive on this gas station.", userName,
                    typeOfGasNeeded);
            System.out.println(message);
            logger.info(message);
            return;
        } catch (NotEnoughGasException e) {
            message = MessageFormat.format(userName + ": Oops, there is no enough {1} gas on this gas station.",
                    userName, typeOfGasNeeded);
            System.out.println(message);
            logger.info(message);
            return;
        } catch (ThereIsNoGasStationHereException e) {
            message = MessageFormat
                    .format("{0}: Oops, no gas station here, we are in the middle of the highway! I will keep looking for one.",
                            userName);
            System.out.println(message);
            logger.info(message);
            return;
        }

        message = MessageFormat.format("{0}: Just filled her up now with {1} liters of {2} and paid {3} euros.",
                userName, amountOfGasNeeded, typeOfGasNeeded, amountOfMoneyToPay);
        System.out.println(message);
        logger.info(message);
    }

    private double getSomeGas(GasStation gasStation, GasType type, double amountInLiters, double maxPricePerLiter)
            throws GasTooExpensiveException, NotEnoughGasException, ThereIsNoGasStationHereException {

        double price = 0.0d;

        if (gasStation != null) {
            price = gasStation.buyGas(type, amountInLiters, maxPricePerLiter);
        } else {

            throw new ThereIsNoGasStationHereException();
        }

        return price;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public Double getGasNeeded() {
        return amountOfGasNeeded;
    }

    public void setGasNeeded(Double gasNeeded) {
        this.amountOfGasNeeded = gasNeeded;
    }

    public Double getMaxMoneyPerLiter() {
        return maxMoneyPerLiter;
    }

    public void setMaxMoneyPerLiter(Double maxMoneyPerLiter) {
        this.maxMoneyPerLiter = maxMoneyPerLiter;
    }

    public void setTypeOfGasNeeded(GasType typeOfGasNeeded) {
        this.typeOfGasNeeded = typeOfGasNeeded;
    }

    public GasType getTypeOfGasNeeded() {
        return typeOfGasNeeded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGate(CyclicBarrier gate) {
        this.gate = gate;
    }

    public CyclicBarrier getGate() {
        return this.gate;
    }

    public Double getAmountOfMoneyToPay() {
        return amountOfMoneyToPay;
    }

}
