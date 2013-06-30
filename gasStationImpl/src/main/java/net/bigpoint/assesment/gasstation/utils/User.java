package net.bigpoint.assesment.gasstation.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

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

    @Override
    public void run() {

        try {
            gate.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (BrokenBarrierException e1) {
            e1.printStackTrace();
        }

        System.out.println(userName + ": Trying to fill some gas up. I need " + amountOfGasNeeded + " liters of "
                + typeOfGasNeeded + " and I will pay " + maxMoneyPerLiter + " euros max.");

        try {
            amountOfMoneyToPay = getSomeGas(gasStation, typeOfGasNeeded, amountOfGasNeeded, maxMoneyPerLiter);
        } catch (GasTooExpensiveException e) {
            System.out
                    .println(userName + ": Woops, " + typeOfGasNeeded + " is too expensive on this gas station, man.");
            return;
        } catch (NotEnoughGasException e) {
            System.out.println(userName + ": Woops, there is no enough " + typeOfGasNeeded
                    + " on this gas station, man.");
            return;
        }
        System.out.println(userName + ": Just filled her up now with " + amountOfGasNeeded + " liters of "
                + typeOfGasNeeded + " and paid " + amountOfMoneyToPay + " euros.");
    }

    private double getSomeGas(GasStation gasStation, GasType type, double amountInLiters, double maxPricePerLiter)
            throws NotEnoughGasException, GasTooExpensiveException {

        double price = 0.0d;

        if (gasStation != null) {
            price = gasStation.buyGas(type, amountInLiters, maxPricePerLiter);
        } else {
            System.out.println(userName
                    + ": Woops, no gas here, we are in the middle of the highway, man! I'll keep looking for one");
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
}
