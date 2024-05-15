package timer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomTimerTest {

    @Test
    void testString2Distribution() {
        // 3.5.1: Give a value among possible distributions
        assertEquals(RandomTimer.randomDistribution.GAUSSIAN, RandomTimer.string2Distribution("GAUSSIAN"));

        // 3.5.2: Give a possible value in lowercase
        assertEquals(RandomTimer.randomDistribution.GAUSSIAN, RandomTimer.string2Distribution("gaussian"));

        // 3.5.3: Give an unexpected value
        assertThrows(IllegalArgumentException.class, () -> RandomTimer.string2Distribution("hello world"));

        // 3.5.4: Give an empty string
        assertThrows(IllegalArgumentException.class, () -> RandomTimer.string2Distribution(""));
    }

    @Test
    void testDistribution2String() {
        // 3.5.5: Give one of the distributions defined by the class
        assertEquals("GAUSSIAN", RandomTimer.distribution2String(RandomTimer.randomDistribution.GAUSSIAN));
    }

    @Test
    void testGetDistributionParam() throws Exception {
        // Case EXP
        RandomTimer expTimer = new RandomTimer(RandomTimer.randomDistribution.EXP, 1.0);
        assertTrue(expTimer.getDistributionParam().contains("rate:"));

        // Case POISSON
        RandomTimer poissonTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 1.0);
        assertTrue(poissonTimer.getDistributionParam().contains("mean:"));

        // Case POSIBILIST
        RandomTimer possibilistTimer = new RandomTimer(RandomTimer.randomDistribution.POSIBILIST, 0, 10);
        assertTrue(possibilistTimer.getDistributionParam().contains("lolim:"));
        assertTrue(possibilistTimer.getDistributionParam().contains("hilim:"));

        // Case GAUSSIAN
        RandomTimer gaussianTimer = new RandomTimer(RandomTimer.randomDistribution.GAUSSIAN, 0, 10);
        assertTrue(gaussianTimer.getDistributionParam().contains("lolim:"));
        assertTrue(gaussianTimer.getDistributionParam().contains("hilim:"));
    }

    @Test
    void testToString() throws Exception {
        // Case EXP
        RandomTimer expTimer = new RandomTimer(RandomTimer.randomDistribution.EXP, 1.0);
        assertTrue(expTimer.toString().contains("EXP rate:"));

        // Case POISSON
        RandomTimer poissonTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 1.0);
        assertTrue(poissonTimer.toString().contains("POISSON mean:"));

        // Case POSIBILIST
        RandomTimer possibilistTimer = new RandomTimer(RandomTimer.randomDistribution.POSIBILIST, 0, 10);
        assertTrue(possibilistTimer.toString().contains("POSIBILIST LoLim:"));
        assertTrue(possibilistTimer.toString().contains("HiLim:"));

        // Case GAUSSIAN
        RandomTimer gaussianTimer = new RandomTimer(RandomTimer.randomDistribution.GAUSSIAN, 0, 10);
        assertTrue(gaussianTimer.toString().contains("GAUSSIAN LoLim:"));
        assertTrue(gaussianTimer.toString().contains("HiLim:"));
    }
}
