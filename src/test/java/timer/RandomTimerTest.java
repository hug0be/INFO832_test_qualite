package timer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomTimerTest {

    @Test
    void testString2Distribution() {
        assertEquals(RandomTimer.randomDistribution.POISSON, RandomTimer.string2Distribution("POISSON"));
        assertEquals(RandomTimer.randomDistribution.EXP, RandomTimer.string2Distribution("exponential"));
        assertEquals(RandomTimer.randomDistribution.GAUSSIAN, RandomTimer.string2Distribution("gaussian"));
        assertEquals(RandomTimer.randomDistribution.POSIBILIST, RandomTimer.string2Distribution("PoSiBiLiSt"));
        assertThrows(IllegalArgumentException.class, () -> RandomTimer.string2Distribution("random"));
    }

    @Test
    void testDistribution2String() {
        assertEquals("POISSON", RandomTimer.distribution2String(RandomTimer.randomDistribution.POISSON));
        assertEquals("EXP", RandomTimer.distribution2String(RandomTimer.randomDistribution.EXP));
        assertEquals("GAUSSIAN", RandomTimer.distribution2String(RandomTimer.randomDistribution.GAUSSIAN));
        assertEquals("POSIBILIST", RandomTimer.distribution2String(RandomTimer.randomDistribution.POSIBILIST));
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
