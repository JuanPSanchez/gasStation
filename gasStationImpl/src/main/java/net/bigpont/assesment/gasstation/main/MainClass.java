package net.bigpont.assesment.gasstation.main;

import java.util.concurrent.CyclicBarrier;

import net.bigpoint.assesment.gasstation.impl.GasStationImpl;
import net.bigpoint.assesment.gasstation.utils.GasStationUtils;
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
        GasStationUtils.getPumpsStatus(gasStation);

        final CyclicBarrier gate = new CyclicBarrier(4); // make the threads start at unison using a barrier gate

        GasStationUtils.createAndActivateUser(gasStation, gate, "Ralph", 30d, 1.4d, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Mary", 40d, 1.48d, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "John", 30d, 1.60d, GasType.DIESEL);

        gate.await(); // go!

        System.out.println("\nAll threads started\n");

        Thread.sleep(5000 * NUMBER_OF_USERS);

        // stats per gas type
        GasStationUtils.getStatsPerGasType(gasStation);

        // stats totals
        GasStationUtils.getStatsTotals(gasStation);

        // pump status afterwards
        GasStationUtils.getPumpsStatus(gasStation);

    }

}
