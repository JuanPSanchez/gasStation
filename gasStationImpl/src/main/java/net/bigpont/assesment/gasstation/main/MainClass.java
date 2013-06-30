package net.bigpont.assesment.gasstation.main;

import java.util.concurrent.CyclicBarrier;

import net.bigpoint.assesment.gasstation.data.User;
import net.bigpoint.assesment.gasstation.impl.GasStationImpl;
import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;

/**
 * 
 * This class implements an example to test the thread-safe implementation GasStationImpl of interface GasStation
 * 
 * @author Juan P. Sánchez
 * 
 */
public class MainClass {

    private static final int NUMBER_OF_USERS = 3;

    public static void main(String[] args) throws Exception {

        System.out.println("Starting...");

        GasPump gasPumpDiesel1 = new GasPump(GasType.DIESEL, 70d);
        GasPump gasPumpDiesel2 = new GasPump(GasType.DIESEL, 30d);
        GasPump gasPumpRegular = new GasPump(GasType.REGULAR, 50d);
        GasPump gasPumpSuper = new GasPump(GasType.SUPER, 50d);

        GasStation gasStation = new GasStationImpl();

        gasStation.addGasPump(gasPumpDiesel1);
        gasStation.addGasPump(gasPumpRegular);
        gasStation.addGasPump(gasPumpSuper);
        gasStation.addGasPump(gasPumpDiesel2);

        gasStation.setPrice(GasType.DIESEL, 1);
        gasStation.setPrice(GasType.REGULAR, 1.45);
        gasStation.setPrice(GasType.SUPER, 1.5);

        // pumps status before
        getPumpsStatus(gasStation);

        final CyclicBarrier gate = new CyclicBarrier(4); // make the threads start at unison using a barrier gate

        createAndActivateUser(gasStation, gate, "Ralph", 30d, 1.4d, GasType.DIESEL);
        createAndActivateUser(gasStation, gate, "Mary", 40d, 1.48d, GasType.DIESEL);
        createAndActivateUser(gasStation, gate, "John", 30d, 1.60d, GasType.DIESEL);

        gate.await();

        System.out.println("\nAll threads started\n");

        Thread.sleep(5000 * NUMBER_OF_USERS);

        // stats per gas type
        getStatsPerGasType(gasStation);

        // stats totals
        getStatsTotals(gasStation);

        // pump status afterwards
        getPumpsStatus(gasStation);

    }

    private static void getStatsPerGasType(GasStation gasStation) {
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

    private static void getStatsTotals(GasStation gasStation) {
        System.out.println("\nTotals: ");
        System.out.println("Earned " + gasStation.getRevenue() + " euros in total.");
        System.out.println("Performed " + gasStation.getNumberOfSales() + " successful sales.");
        System.out.println("Cancelled " + gasStation.getNumberOfCancellationsNoGas()
                + " sales due to insufficient gas available.");
        System.out.println("Cancelled " + gasStation.getNumberOfCancellationsTooExpensive()
                + " sales due the gas being too expensive.");
    }

    private static void getPumpsStatus(GasStation gasStation) {
        System.out.println("\nStatus of the Pumps:");

        for (GasPump gasPump : gasStation.getGasPumps()) {
            System.out.println("Gas pump of " + gasPump.getGasType() + ". " + gasPump.getRemainingAmount()
                    + " liters of gas remaining.");
        }
    }

    private static void createAndActivateUser(GasStation gasStation, final CyclicBarrier gate, String userName,
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
