package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.OneShotTimer;
import timer.PeriodicTimer;

import static org.junit.jupiter.api.Assertions.*;

public class DiscreteActionTest {

    private DiscreteAction discreteActionlittle;
    private DiscreteAction discreteActionBig;
    private DiscreteAction discreteActionMid;
    private DiscreteAction discreteActionOne;
    private DiscreteAction discreteActionZeros;
    @BeforeEach
    void setUp() {
        discreteActionlittle = new DiscreteAction(14, "toString", new PeriodicTimer(4, 7));
        discreteActionBig = new DiscreteAction(14, "toString", new PeriodicTimer(4, 2147483647));
        discreteActionOne = new DiscreteAction(14, "toString", new PeriodicTimer(4, 1));
        discreteActionZeros = new DiscreteAction(14, "toString", new PeriodicTimer(4, 1));
        discreteActionMid = new DiscreteAction(14, "toString", new PeriodicTimer(4, 3647));
    }

    @Test
    void spendTimeLittleAction() {
        try {
            discreteActionlittle.spendTime(0);
            assertEquals(7, (int) discreteActionlittle.getCurrentLapsTime());
        } catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    void spendTimeLittleActionNoZerosValue() {
        try {
            discreteActionlittle.spendTime(5);
            assertEquals(2, (int) discreteActionlittle.getCurrentLapsTime());
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    void spendTimeLittleActionNegativeValue() {
        assertThrowsExactly(IllegalArgumentException.class, () -> discreteActionlittle.spendTime(-10));
    }

    @Test
    void spendTimeBigAction() {
        try {
            discreteActionBig.spendTime(2147483647);
            assertEquals(0, (int) discreteActionBig.getCurrentLapsTime());
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    void spendTimeMidAction(){
        try{
            discreteActionMid.spendTime(100000);
            assertEquals(0, (int) discreteActionMid.getCurrentLapsTime());
        } catch (NullPointerException e){fail();}
    }

    @Test
    void compareTo(){
        DiscreteAction discreteActionWithoutTime = new DiscreteAction(14,"toString", new OneShotTimer(0));
        discreteActionWithoutTime.compareTo( new DiscreteAction(14,"toString", new OneShotTimer(0)));
        double inf = Double.POSITIVE_INFINITY;
    }

    @Test
    void hasNext(){
        assertTrue(discreteActionlittle.hasNext());
        DiscreteAction discerteActionWithoutTime = new DiscreteAction(14,"toString", new OneShotTimer(0));
        assertFalse(discerteActionWithoutTime.hasNext());

    }

    @Test
    void Next(){
        DiscreteAction discerteActionWithoutTime = new DiscreteAction(14,"toString", new OneShotTimer(0));
        assertThrows(NullPointerException.class, ()-> discerteActionWithoutTime.next());
        discreteActionlittle.next();
        assertEquals(7, discreteActionlittle.getCurrentLapsTime());
    }

}
