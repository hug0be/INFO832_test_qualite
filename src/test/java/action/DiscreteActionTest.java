package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void spendTime(){
        discreteActionlittle.spendTime(0);
        assertEquals(7, (int) discreteActionlittle.getCurrentLapsTime());
        discreteActionlittle.spendTime(5);
        assertEquals(2, (int) discreteActionlittle.getCurrentLapsTime());
        assertThrowsExactly(IllegalArgumentException.class, () -> discreteActionlittle.spendTime(-10));
        discreteActionBig.spendTime(2147483647);
        assertEquals(0, (int) discreteActionBig.getCurrentLapsTime());
        discreteActionMid.spendTime(100000);
        assertEquals(0, (int) discreteActionMid.getCurrentLapsTime());
    }

    @Test
    void compareTo(){
        DiscreteAction discreteActionWithoutTime = new DiscreteAction();
        discreteActionWithoutTime.compareTo( new DiscreteAction());
        double inf = Double.POSITIVE_INFINITY;
    }

    @Test
    void hasNext(){
        assertTrue(discreteActionlittle.hasNext());
        DiscreteAction discerteActionWithoutTime = new DiscreteAction();
        assertFalse(discerteActionWithoutTime.hasNext());

    }

    @Test
    void Next(){
        DiscreteAction discerteActionWithoutTime = new DiscreteAction();
        assertThrows(NullPointerException.class, ()-> discerteActionWithoutTime.next());
        discreteActionlittle.next();
        assertEquals(7, discreteActionlittle.getCurrentLapsTime());
    }

}
