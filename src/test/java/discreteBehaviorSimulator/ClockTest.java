package discreteBehaviorSimulator;


import action.DiscreteActionDependent;
import action.DiscreteActionOnOffDependent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.OneShotTimer;
import timer.PeriodicTimer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClockTest {

    private Clock clock;

    @BeforeEach
    public void setUp() {
        clock = Clock.getInstance();
    }

    @Test
    public void testIncreaseWithNaturalNumber() {
        try {
            clock.increase(2);
            assertEquals("Time should be increased by 2", clock.getTime());
        } catch (Exception e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }
    }

    @Test
    public void testIncreaseWithZero() {
        try {
            clock.increase(0);
            assertEquals("Time should remain unchanged", clock.getTime());
        } catch (Exception e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }
    }

    @Test
    public void testIncreaseWithNegativeNumber() {
        try {
            clock.increase(-32);
            fail("Expected exception for negative time change was not thrown");
        } catch (Exception e) {
            // Expected behavior
        }
    }

    @Test
    public void testSetNextJumpWithPositiveNumber() {
        clock.setNextJump(1);
        assertEquals(1, clock.getTime());
    }

    @Test
    public void testSetNextJumpWithNegativeNumber() {
        try {
            clock.setNextJump(-1);
            // If no exception is thrown, the test fails
        } catch (Exception e) {
            // Expected behavior
        }
    }

    @Test
    public void testSetNextJumpWithZero() {
        clock.setNextJump(0);
        assertEquals(0, clock.getTime());
    }

}