package net.bigpont.assesment.gasstation.utils.test;

import java.util.ArrayList;
import java.util.Collection;

import net.bigpoint.assesment.gasstation.utils.GasStationUtils;
import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class GasStationUtilsTest {

    @Mock
    private Logger loggerMock;

    @Mock
    private GasStation gasStationMock;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(GasStationUtilsTest.class);

    }

    @Test
    public void shouldShowStatsPerGasType() {

        // Given

        // When
        Mockito.when(gasStationMock.getAmountSold(GasType.DIESEL)).thenReturn(500d);
        Mockito.when(gasStationMock.getAmountSold(GasType.SUPER)).thenReturn(600d);
        Mockito.when(gasStationMock.getAmountSold(GasType.REGULAR)).thenReturn(700d);

        Mockito.when(gasStationMock.getRevenue(GasType.DIESEL)).thenReturn(1000d);
        Mockito.when(gasStationMock.getRevenue(GasType.SUPER)).thenReturn(1200d);
        Mockito.when(gasStationMock.getRevenue(GasType.REGULAR)).thenReturn(1400d);

        Mockito.when(gasStationMock.getNumberOfSales(GasType.DIESEL)).thenReturn(15);
        Mockito.when(gasStationMock.getNumberOfSales(GasType.SUPER)).thenReturn(20);
        Mockito.when(gasStationMock.getNumberOfSales(GasType.REGULAR)).thenReturn(22);

        Mockito.when(gasStationMock.getNumberOfCancellationsNoGas(GasType.DIESEL)).thenReturn(4);
        Mockito.when(gasStationMock.getNumberOfCancellationsNoGas(GasType.SUPER)).thenReturn(5);
        Mockito.when(gasStationMock.getNumberOfCancellationsNoGas(GasType.REGULAR)).thenReturn(7);

        Mockito.when(gasStationMock.getNumberOfCancellationsTooExpensive(GasType.DIESEL)).thenReturn(2);
        Mockito.when(gasStationMock.getNumberOfCancellationsTooExpensive(GasType.SUPER)).thenReturn(1);
        Mockito.when(gasStationMock.getNumberOfCancellationsTooExpensive(GasType.REGULAR)).thenReturn(1);

        GasStationUtils.setLogger(loggerMock);
        GasStationUtils.showStatsPerGasType(gasStationMock);

        // Then
        Mockito.verify(loggerMock, Mockito.times(1)).info("Sold 500 liters of DIESEL.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Sold 600 liters of SUPER.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Sold 700 liters of REGULAR.");

        Mockito.verify(loggerMock, Mockito.times(1)).info("Earned 1,000 euros for DIESEL.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Earned 1,200 euros for SUPER.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Earned 1,400 euros for REGULAR.");

        Mockito.verify(loggerMock, Mockito.times(1)).info("Performed 15 successful sales for DIESEL.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Performed 20 successful sales for SUPER.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Performed 22 successful sales for REGULAR.");

        Mockito.verify(loggerMock, Mockito.times(1)).info(
                "Cancelled 4 sales for DIESEL due to insufficient gas available.");
        Mockito.verify(loggerMock, Mockito.times(1)).info(
                "Cancelled 5 sales for SUPER due to insufficient gas available.");
        Mockito.verify(loggerMock, Mockito.times(1)).info(
                "Cancelled 7 sales for REGULAR due to insufficient gas available.");

        Mockito.verify(loggerMock, Mockito.times(1)).info(
                "Cancelled 2 sales for DIESEL due the gas being too expensive.");
        Mockito.verify(loggerMock, Mockito.times(1)).info(
                "Cancelled 1 sales for SUPER due the gas being too expensive.");
        Mockito.verify(loggerMock, Mockito.times(1)).info(
                "Cancelled 1 sales for REGULAR due the gas being too expensive.");

    }

    @Test
    public void shouldShowStatsTotals() {

        // Given

        // When
        Mockito.when(gasStationMock.getRevenue()).thenReturn(3600d);
        Mockito.when(gasStationMock.getNumberOfSales()).thenReturn(57);
        Mockito.when(gasStationMock.getNumberOfCancellationsNoGas()).thenReturn(16);
        Mockito.when(gasStationMock.getNumberOfCancellationsTooExpensive()).thenReturn(4);

        GasStationUtils.setLogger(loggerMock);
        GasStationUtils.showStatsTotals(gasStationMock);

        // Then
        Mockito.verify(loggerMock, Mockito.times(1)).info("Earned 3,600 euros in total.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Performed 57 successful sales.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Cancelled 16 sales due to insufficient gas available.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Cancelled 4 sales due to insufficient gas available.");

    }

    @Test
    public void shouldShowThePumpsStatusForGasStation() {

        // Given
        GasPump gasPump1 = new GasPump(GasType.DIESEL, 40d);
        GasPump gasPump2 = new GasPump(GasType.SUPER, 50d);
        GasPump gasPump3 = new GasPump(GasType.REGULAR, 60d);

        Collection<GasPump> gasPumpList = new ArrayList<GasPump>();
        gasPumpList.add(gasPump1);
        gasPumpList.add(gasPump2);
        gasPumpList.add(gasPump3);

        // When
        Mockito.when(gasStationMock.getGasPumps()).thenReturn(gasPumpList);

        GasStationUtils.setLogger(loggerMock);
        GasStationUtils.showPumpsStatus(gasStationMock);

        // Then
        Mockito.verify(loggerMock, Mockito.times(1)).info("Gas pump of DIESEL. 40 liters of gas remaining.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Gas pump of SUPER. 50 liters of gas remaining.");
        Mockito.verify(loggerMock, Mockito.times(1)).info("Gas pump of REGULAR. 60 liters of gas remaining.");

    }

}
