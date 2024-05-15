package timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.*;

class TimeBoundedTimerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void constructorNormWithOneShotTimer(){
        OneShotTimer oneshot = new OneShotTimer(3);
        TimeBoundedTimer timer_resulting = new TimeBoundedTimer(oneshot, 7);
        assertNotNull(timer_resulting);
    }

    @Test
    void constructorNormWithPeriodicTimer(){
        PeriodicTimer period = new PeriodicTimer(3,4);
        TimeBoundedTimer timer_resulting = new TimeBoundedTimer(period,7);
        assertTrue(timer_resulting.hasNext());
    }

    @Test
    void constructorWithTimerInitToZero(){
        PeriodicTimer period = new PeriodicTimer(0,0);
        TimeBoundedTimer timer_resulting = new TimeBoundedTimer(period, 7);
        assertNotNull(timer_resulting);
    }

    @Test
    void constructorWithTimerInitToNull(){
        TimeBoundedTimer timer_resulting = new TimeBoundedTimer(null, 7);
        assertNotNull(timer_resulting);
    }

    @Test
    void constructorInitWithZero(){
        PeriodicTimer period = new PeriodicTimer(3,4);
        TimeBoundedTimer timer_resulting = new TimeBoundedTimer(period , 0);
        assertNotNull(timer_resulting);
    }

    @Test
    void nextWithFollowingTime(){
        PeriodicTimer period = new PeriodicTimer(3,4);
        TimeBoundedTimer timer = new TimeBoundedTimer(period, 4);
        assertEquals(3, timer.next());

    }

    @Test
    void nextWithoutFollowingTime(){
        OneShotTimer oneshot = new OneShotTimer(3);
        TimeBoundedTimer timer = new TimeBoundedTimer(period, 7);
        assertNull(timer.next());
    }
}