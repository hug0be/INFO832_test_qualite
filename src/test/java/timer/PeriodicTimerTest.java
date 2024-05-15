package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicTimerTest {

    private PeriodicTimer periodicTimerNormal;
    private PeriodicTimer periodicTimerWithRandom;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize RandomTimer
        RandomTimer randomTimer = new RandomTimer(RandomTimer.randomDistribution.GAUSSIAN, 20);

        // Initialize PeriodicTimer with fixed period and initial value
        periodicTimerNormal = new PeriodicTimer(4, 5);

        // Initialize PeriodicTimer with fixed period, initial value, and RandomTimer
        periodicTimerWithRandom = new PeriodicTimer(4, 5, randomTimer);
    }

    @Test
    void testInitializationWithParameters() {
        // 3.4.1: Initialization with period, at, and RandomTimer
        assertNotNull(periodicTimerNormal);
        assertNotNull(periodicTimerWithRandom);
    }

    @Test
    void testNextNormalUsage() {
        // 3.4.2: Normal usage of next()

        // For periodicTimerNormal
        assertEquals(5, periodicTimerNormal.next());
        assertEquals(4, periodicTimerNormal.next());
        assertEquals(4, periodicTimerNormal.next());

        // For periodicTimerWithRandom
        // The exact value will depend on the random variation, so we can't assert a fixed value
        int nextValue = periodicTimerWithRandom.next();
        assertTrue(nextValue >= -15 && nextValue <= 35); // Assuming Gaussian distribution around 20 with wide spread
    }

    @Test
    void testHasNext() {
        // hasNext() should always return true
        assertTrue(periodicTimerNormal.hasNext());
        assertTrue(periodicTimerWithRandom.hasNext());
    }
}
