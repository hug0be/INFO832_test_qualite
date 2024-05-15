package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneShotTimerTest {

    @Test
    void testNextWithPositiveInteger() {
        // 3.3.1: Give a positive integer (1)
        OneShotTimer timer = new OneShotTimer(1);
        assertEquals(1, timer.next());
    }

    @Test
    void testNextWithLargeInteger() {
        // 3.3.2: Give a large integer (2147483647)
        OneShotTimer timer = new OneShotTimer(2147483647);
        assertEquals(2147483647, timer.next());
    }

    @Test
    void testNextWithZero() {
        // 3.3.3: Give zero (0)
        OneShotTimer timer = new OneShotTimer(0);
        assertEquals(0, timer.next());
    }

    @Test
    void testNextWithNegativeInteger() {
        // 3.3.4: Give a negative integer (-2)
        OneShotTimer timer = new OneShotTimer(-2);
        assertEquals(-2, timer.next());
    }

    @Test
    void testHasNextBeforeCallingNext() {
        // 3.3.5: Check hasNext() before calling next()
        OneShotTimer timer = new OneShotTimer(1);
        assertTrue(timer.hasNext());
    }

    @Test
    void testHasNextAfterCallingNext() {
        // 3.3.6: Check hasNext() after calling next()
        OneShotTimer timer = new OneShotTimer(1);
        timer.next(); // Call next
        assertFalse(timer.hasNext());
    }
}
