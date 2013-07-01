package net.bigpoint.assessment.gasstation.main;

import java.util.concurrent.CyclicBarrier;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.impl.GasStationImpl;
import net.bigpoint.assessment.gasstation.utils.GasStationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        Logger logger = LoggerFactory.getLogger(MainClass.class);

        String message = "Starting...";
        logger.debug(message);

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
        GasStationUtils.showPumpsStatus(gasStation);

        final CyclicBarrier gate = new CyclicBarrier(4); // make the threads start at unison using a barrier gate

        message = "Setting cyclic barrier to 4, three users and main threads.";
        logger.debug(message);

        GasStationUtils.createAndActivateUser(gasStation, gate, "Ralph", 30d, 1.4d, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "Mary", 40d, 1.48d, GasType.DIESEL);
        GasStationUtils.createAndActivateUser(gasStation, gate, "John", 30d, 1.60d, GasType.DIESEL);

        gate.await(); // go!

        message = "\nAll threads started\n";
        logger.debug(message);

        Thread.sleep(5000 * NUMBER_OF_USERS);

        // stats per gas type
        GasStationUtils.showStatsPerGasType(gasStation);

        // stats totals
        GasStationUtils.showStatsTotals(gasStation);

        // pump status afterwards
        GasStationUtils.showPumpsStatus(gasStation);

    }
}
