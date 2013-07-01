package net.bigpoint.assesment.gasstation.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

/**
 * 
 * This class implements interface GasStation to model a gas station functionality
 * 
 * @author Juan P. Sánchez
 * 
 */
public class GasStationImpl implements GasStation {

    private List<GasPump> gasPumps = new ArrayList<GasPump>();
    private Map<GasType, Double> gasPrices = new HashMap<GasType, Double>();

    private Map<GasType, Double> litersOfGasSold = new HashMap<GasType, Double>();
    private Map<GasType, Double> revenueEarned = new HashMap<GasType, Double>();
    private Map<GasType, Integer> numberOfSuccessfulSales = new HashMap<GasType, Integer>();
    private Map<GasType, Integer> cancellationsNoGas = new HashMap<GasType, Integer>();
    private Map<GasType, Integer> cancellationsTooExpensive = new HashMap<GasType, Integer>();

    public GasStationImpl() {

        for (GasType gasType : GasType.values()) {
            gasPrices.put(gasType, 0.0d);
            litersOfGasSold.put(gasType, 0.0d);
            revenueEarned.put(gasType, 0.0d);
            numberOfSuccessfulSales.put(gasType, 0);
            cancellationsNoGas.put(gasType, 0);
            cancellationsTooExpensive.put(gasType, 0);
        }

    }

    public void addGasPump(GasPump pump) {
        gasPumps.add(pump);
    }

    public Collection<GasPump> getGasPumps() {
        return gasPumps;
    }

    public double buyGas(GasType type, double amountInLiters, double maxPricePerLiter) throws NotEnoughGasException,
            GasTooExpensiveException {

        Double gasPrice = getGasPrice(type);
        if (gasPrice > maxPricePerLiter) {
            throwNewGasTooExpensiveException(type);
        }

        boolean isThereEnoughGas = getSuitableGasPump(type, amountInLiters);
        if (!isThereEnoughGas) {
            throwNewNotEnoughGasException(type);
        }

        return gasPrice * amountInLiters;
    }

    private synchronized Double getGasPrice(GasType type) {
        return gasPrices.get(type);
    }

    private synchronized boolean getSuitableGasPump(GasType type, double amountInLiters) {

        boolean thereIsEnoughGasLeft = false;

        for (GasPump gasPump : gasPumps) {

            if (gasPump.getGasType().equals(type) && gasPump.getRemainingAmount() >= amountInLiters) {

                gasPump.pumpGas(amountInLiters);

                this.litersOfGasSold.put(type, this.litersOfGasSold.get(type) + amountInLiters);
                this.revenueEarned.put(type, this.revenueEarned.get(type) + amountInLiters * gasPrices.get(type));
                this.numberOfSuccessfulSales.put(type, this.numberOfSuccessfulSales.get(type) + 1);

                thereIsEnoughGasLeft = true;
                break;
            }

        }
        return thereIsEnoughGasLeft;
    }

    private synchronized void throwNewGasTooExpensiveException(GasType type) throws GasTooExpensiveException {
        this.cancellationsTooExpensive.put(type, this.cancellationsTooExpensive.get(type) + 1);
        throw new GasTooExpensiveException();
    }

    private synchronized void throwNewNotEnoughGasException(GasType type) throws NotEnoughGasException {
        int value = this.cancellationsNoGas.get(type);
        this.cancellationsNoGas.put(type, value + 1);
        throw new NotEnoughGasException();
    }

    public double getAmountSold(GasType type) {
        return litersOfGasSold.get(type);
    }

    public double getRevenue() {
        return sumDoubleValuesForAllGasTypes(revenueEarned);
    }

    public double getRevenue(GasType type) {
        return revenueEarned.get(type);
    }

    public int getNumberOfSales() {
        return sumIntegerValuesForAllGasTypes(numberOfSuccessfulSales);
    }

    public int getNumberOfSales(GasType type) {
        return numberOfSuccessfulSales.get(type);
    }

    public int getNumberOfCancellationsNoGas() {
        return sumIntegerValuesForAllGasTypes(cancellationsNoGas);
    }

    public int getNumberOfCancellationsNoGas(GasType type) {
        return cancellationsNoGas.get(type);
    }

    public int getNumberOfCancellationsTooExpensive() {
        return sumIntegerValuesForAllGasTypes(cancellationsTooExpensive);
    }

    public int getNumberOfCancellationsTooExpensive(GasType type) {
        return cancellationsTooExpensive.get(type);
    }

    public double getPrice(GasType type) {
        return gasPrices.get(type);
    }

    public void setPrice(GasType type, double price) {
        gasPrices.put(type, price);
    }

    private Double sumDoubleValuesForAllGasTypes(Map<GasType, Double> values) {

        Double total = 0.0d;

        for (GasType gasType : values.keySet()) {

            total = total + values.get(gasType);

        }

        return total;
    }

    private Integer sumIntegerValuesForAllGasTypes(Map<GasType, Integer> values) {

        Integer total = 0;

        for (GasType gasType : values.keySet()) {

            total = total + values.get(gasType);

        }

        return total;
    }
}
