package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.PeriodicTimer;

import static org.junit.jupiter.api.Assertions.*;

public class DiscreteActionTest {

    private DiscreteAction discreteActionlittle;
    private DiscreteAction discreteActionBig;
    private DiscreteAction discreteActionMid;

    @BeforeEach
    void setUp() {
        discreteActionlittle = new DiscreteAction(14, "toString", new PeriodicTimer(4, 7));
        discreteActionBig = new DiscreteAction(14, "toString", new PeriodicTimer(4, 2147483647));
        discreteActionMid = new DiscreteAction(14, "toString", new PeriodicTimer(4, 3647));
    }

    @Test
    void spendTime(){
        discreteActionlittle.spendTime(0);
        assertEquals(7, discreteActionlittle.getCurrentLapsTime());
        discreteActionlittle.spendTime(5);
        assertEquals(2, discreteActionlittle.getCurrentLapsTime());
        assertThrowsExactly(IllegalArgumentException.class, () -> discreteActionlittle.spendTime(-10));
        discreteActionBig.spendTime(2147483647);
        assertEquals(null, discreteActionBig.getCurrentLapsTime());
        discreteActionMid.spendTime(100000);
        assertEquals(0, discreteActionMid.getCurrentLapsTime());
    }

    @Test
    void compareTo(){
        
    }

}
