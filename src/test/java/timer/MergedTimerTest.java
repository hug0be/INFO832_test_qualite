package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class MergedTimerTest {
    private MergedTimer doneTimer, almostDoneTimer, normalTimer;

    @BeforeEach
    void setUp() {
        // Initialize timers to be merged
        DateTimer dummyTimer1 = new DateTimer(new Vector<>(Arrays.asList(1, 5, 6)));
        DateTimer dummyTimer2 = new DateTimer(new Vector<>(Arrays.asList(1, 2)));
        DateTimer dummyAlmostDoneTimer = new DateTimer(new Vector<>(1));
        DateTimer dummyDoneTimer = new DateTimer(new Vector<>());

        // Initialize merged timers
        normalTimer = new MergedTimer(dummyTimer1, dummyTimer2);
        almostDoneTimer = new MergedTimer(dummyAlmostDoneTimer, dummyDoneTimer);
        doneTimer = new MergedTimer(dummyDoneTimer, dummyDoneTimer);
    }

    @Test
    void hasNext() {
        assertTrue(normalTimer.hasNext());
        assertFalse(doneTimer.hasNext());
        assertTrue(almostDoneTimer.hasNext());
    }

    @Test
    void next() {
        // Merged timers with a value should return the sum of them (1+2 = 1)
        assertEquals(2, normalTimer.next());
        // Same here (2+5 = 7)
        assertEquals(7, normalTimer.next());
        // If there's only one timer left, return the value of the remaining timer
        assertEquals(6, normalTimer.next());

        // Done timers should throw NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> normalTimer.next());
        assertThrows(NoSuchElementException.class, () -> doneTimer.next());
    }
}