package discrete_behavior_simulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClockTest {

    private Clock clock;

    @BeforeEach
    public void setUp() {
        clock = Clock.getInstance();
    }


    @Test
    void testIncreaseWithZero() {
        int firstTime = (int) clock.getTime();
        try {
            clock.increase(0);
            assertEquals(firstTime, clock.getTime());
        } catch (Exception e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }
    }

    @Test
    void testIncreaseWithNaturalNumber() {
        try {
            clock.setNextJump(2);
            clock.increase(2);
            assertEquals(2, clock.getTime());
        } catch (Exception e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }
    }

    @Test
    void testIncreaseWithNegativeNumber() {
        try {
            clock.increase(-32);
            fail("Expected exception for negative time change was not thrown");
        } catch (Exception ignored) {
        }
    }

}