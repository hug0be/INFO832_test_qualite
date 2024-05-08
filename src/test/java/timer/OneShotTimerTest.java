package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneShotTimerTest {
    public OneShotTimer oneShotTimer = new OneShotTimer(1);


    @Test
    void constructorsTest() {
        assertThrows(IllegalArgumentException.class, () -> new OneShotTimer(-1));
    }
    @Test
    void hasNext() {
        assertTrue(oneShotTimer.hasNext());
        oneShotTimer.next();
        assertFalse(oneShotTimer.hasNext());
    }

    @Test
    void next() {
        assertEquals(1, oneShotTimer.next());
        assertThrows(IllegalArgumentException.class, () -> oneShotTimer.next());
    }
}