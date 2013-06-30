package net.bigpoint.assessment.gasstation;

import java.util.Collection;

import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

/**
 * This interface is for a gas station.
 * 
 * The implementations should be thread-safe!
 * 
 */
public interface GasStation {

    /**
     * Add a gas pump to this station. This is used to set up this station.
     * 
     * @param pump
     *            the gas pump
     */
    void addGasPump(GasPump pump);

    /**
     * Get all gas pumps that are currently associated with this gas station.
     * 
     * Modifying the resulting collection should not affect this gas station.
     * 
     * @return A collection of all gas pumps.
     */
    Collection<GasPump> getGasPumps();

    /**
     * Simulates a customer wanting to buy a specific amount of gas.
     * 
     * @param type
     *            The type of gas the customer wants to buy
     * @param amountInLiters
     *            The amount of gas the customer wants to buy. Nothing less than this amount is acceptable!
     * @param maxPricePerLiter
     *            The maximum price the customer is willing to pay per liter
     * @return the price the customer has to pay for this transaction
     * @throws NotEnoughGasException
     *             Should be thrown in case not enough of this type of gas is available
     * @throws GasTooExpensiveException
     *             Should be thrown if gas is not sold at the requested price (or any lower price)
     */
    double buyGas(GasType type, double amountInLiters, double maxPricePerLiter) throws NotEnoughGasException,
            GasTooExpensiveException;

    /**
     * Get the amount of litres sold of a specified type of gas.
     * 
     * @param type
     *            the type of gas
     * @return the amount of litres sold of that type of gas
     */
    double getAmountSold(GasType type);

    /**
     * @return the total revenue generated
     */
    double getRevenue();

    /**
     * Returns the total revenue generated for a specific type of gas
     * 
     * @param type
     *            the type of gas
     * @return the revenue generated
     */
    double getRevenue(GasType type);

    /**
     * Returns the number of successful sales. This should not include cancelled sales.
     * 
     * @return the number of sales that were successful
     */
    int getNumberOfSales();

    /**
     * Returns the number of successful sales for a specific type of gas. This should not include cancelled sales.
     * 
     * @param type
     *            the type of gas
     * @return the number of sales that were successful
     */
    int getNumberOfSales(GasType type);

    /**
     * @return the number of cancelled transactions due to not enough gas being available
     */
    int getNumberOfCancellationsNoGas();

    /**
     * Returns the number of cancelled transactions due to the gas being more expensive than what the customer wanted to
     * pay
     * 
     * @return the number of cancelled transactions
     */
    int getNumberOfCancellationsTooExpensive();

    /**
     * Return the number of cancelled transactions for a specific type of gas due to not enough gas being available
     * 
     * @param type
     *            the type of gas
     * @return the number of cancelled transactions
     */
    int getNumberOfCancellationsNoGas(GasType type);

    /**
     * Returns the number of cancelled transactions for a specific type of gas due to the gas being more expensive than
     * what the customer wanted to pay
     * 
     * @param type
     *            the type of gas
     * @return the number of cancelled transactions
     */
    int getNumberOfCancellationsTooExpensive(GasType type);

    /**
     * Get the price for a specific type of gas
     * 
     * @param type
     *            the type of gas
     * @return the price per liter for this type of gas
     */
    double getPrice(GasType type);

    /**
     * Set a new price for a specific type of gas
     * 
     * @param type
     *            the type of gas
     * @param price
     *            the new price per liter for this type of gas
     */
    void setPrice(GasType type, double price);

}