package net.bigpoint.assesment.gasstation.utils;

import java.util.concurrent.CyclicBarrier;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;

/**
 * 
 * This class contains some methods needed to test the code properly
 * 
 * @author Juan P. Sánchez
 * 
 */
public class GasStationUtils {

    public static void getStatsPerGasType(GasStation gasStation) {
        System.out.println("\n");
        for (GasType gasType : GasType.values()) {
            System.out.println("Sold " + gasStation.getAmountSold(gasType) + " liters of " + gasType + ".");
            System.out.println("Earned " + gasStation.getRevenue(gasType) + " euros for " + gasType + ".");
            System.out.println("Performed " + gasStation.getNumberOfSales(gasType) + " successful sales for " + gasType
                    + ".");
            System.out.println("Cancelled " + gasStation.getNumberOfCancellationsNoGas(gasType) + " sales for "
                    + gasType + " due to insufficient gas available.");
            System.out.println("Cancelled " + gasStation.getNumberOfCancellationsTooExpensive(gasType) + " sales for "
                    + gasType + " due the gas being too expensive.\n");
        }
    }

    public static void getStatsTotals(GasStation gasStation) {
        System.out.println("\nTotals: ");
        System.out.println("Earned " + gasStation.getRevenue() + " euros in total.");
        System.out.println("Performed " + gasStation.getNumberOfSales() + " successful sales.");
        System.out.println("Cancelled " + gasStation.getNumberOfCancellationsNoGas()
                + " sales due to insufficient gas available.");
        System.out.println("Cancelled " + gasStation.getNumberOfCancellationsTooExpensive()
                + " sales due the gas being too expensive.");
    }

    public static void getPumpsStatus(GasStation gasStation) {
        System.out.println("\nStatus of the Pumps:");

        for (GasPump gasPump : gasStation.getGasPumps()) {
            System.out.println("Gas pump of " + gasPump.getGasType() + ". " + gasPump.getRemainingAmount()
                    + " liters of gas remaining.");
        }
    }

    public static void createAndActivateUser(GasStation gasStation, final CyclicBarrier gate, String userName,
            Double gasNeeded, Double maxMoneyPerLiter, GasType gasType) {
        User user = new User();

        user.setGasStation(gasStation);
        user.setGate(gate);
        user.setUserName(userName);
        user.setGasNeeded(gasNeeded);
        user.setMaxMoneyPerLiter(maxMoneyPerLiter);
        user.setTypeOfGasNeeded(gasType);

        Thread someThread = new Thread(user);
        someThread.start();

    }

}
