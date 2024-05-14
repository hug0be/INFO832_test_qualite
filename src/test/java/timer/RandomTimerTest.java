package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomTimerTest {
    public RandomTimer poissonTimer, uniformTimer, exponentialTimer, gaussianTimer;

    @BeforeEach
    void setUp() throws Exception {
        poissonTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 1);
        gaussianTimer = new RandomTimer(RandomTimer.randomDistribution.GAUSSIAN, 1);
    }

    @Test
    void string2Distribution() {
        assertEquals(RandomTimer.randomDistribution.POISSON, RandomTimer.string2Distribution("POISSON"));
        assertEquals(RandomTimer.randomDistribution.EXP, RandomTimer.string2Distribution("exponential"));
        assertEquals(RandomTimer.randomDistribution.GAUSSIAN, RandomTimer.string2Distribution("gaussian"));
        assertEquals(RandomTimer.randomDistribution.POSIBILIST, RandomTimer.string2Distribution("PoSiBiLiSt"));
        assertThrows(IllegalArgumentException.class, () -> RandomTimer.string2Distribution("random"));
    }

    @Test
    void distribution2String() {
        assertEquals("POISSON", RandomTimer.distribution2String(RandomTimer.randomDistribution.POISSON));
        assertEquals("EXP", RandomTimer.distribution2String(RandomTimer.randomDistribution.EXP));
        assertEquals("GAUSSIAN", RandomTimer.distribution2String(RandomTimer.randomDistribution.GAUSSIAN));
        assertEquals("POSIBILIST", RandomTimer.distribution2String(RandomTimer.randomDistribution.POSIBILIST));
    }

    @Test
    void getDistribution() {
        assertEquals("POISSON", poissonTimer.getDistribution());
    }

    @Test
    void getDistributionParam() {

    }

    @Test
    void getMean() {
    }

    @Test
    void next() {
    }

    @Test
    void hasNext() {
    }
}