package net.bigpoint.assesment.gasstation.utils;

import java.text.MessageFormat;
import java.util.concurrent.CyclicBarrier;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class contains some methods needed to test the code properly
 * 
 * @author Juan P. Sánchez
 * 
 */
public class GasStationUtils {

    private static Logger logger = LoggerFactory.getLogger(GasStationUtils.class);

    public static void showStatsPerGasType(GasStation gasStation) {

        String message = "";

        System.out.println("\n");
        for (GasType gasType : GasType.values()) {

            message = MessageFormat.format("Sold {0} liters of {1}.", gasStation.getAmountSold(gasType), gasType);
            System.out.println(message);
            logger.info(message);

            message = MessageFormat.format("Earned {0} euros for {1}.", gasStation.getRevenue(gasType), gasType);
            System.out.println(message);
            logger.info(message);

            message = MessageFormat.format("Performed {0} successful sales for {1}.",
                    gasStation.getNumberOfSales(gasType), gasType);
            System.out.println(message);
            logger.info(message);

            message = MessageFormat.format("Cancelled {0} sales for {1} due to insufficient gas available.",
                    gasStation.getNumberOfCancellationsNoGas(gasType), gasType);
            System.out.println(message);
            logger.info(message);

            message = MessageFormat.format("Cancelled {0} sales for {1} due the gas being too expensive.",
                    gasStation.getNumberOfCancellationsTooExpensive(gasType), gasType);
            System.out.println(message + "\n");
            logger.info(message);
        }
    }

    public static void showStatsTotals(GasStation gasStation) {

        String message = "\nTotals: ";
        System.out.println(message);
        logger.info(message);

        message = MessageFormat.format("Earned {0} euros in total.", gasStation.getRevenue());
        System.out.println(message);
        logger.info(message);

        message = MessageFormat.format("Performed {0} successful sales.", gasStation.getNumberOfSales());
        System.out.println(message);
        logger.info(message);

        message = MessageFormat.format("Cancelled {0} sales due to insufficient gas available.",
                gasStation.getNumberOfCancellationsNoGas());
        System.out.println(message);
        logger.info(message);

        message = MessageFormat.format("Cancelled {0} sales due to insufficient gas available.",
                gasStation.getNumberOfCancellationsTooExpensive());
        System.out.println(message);
        logger.info(message);

    }

    public static void showPumpsStatus(GasStation gasStation) {
        String message = "\nStatus of the Pumps:";
        System.out.println(message);
        logger.info(message);

        for (GasPump gasPump : gasStation.getGasPumps()) {
            message = MessageFormat.format("Gas pump serving {0}. {1} liters of gas remaining.", gasPump.getGasType(),
                    gasPump.getRemainingAmount());
            System.out.println(message);
            logger.info(message);

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

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger theLogger) {
        logger = theLogger;
    }

}
