package timer;

import action.DiscreteActionDependent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DateTimerTest {

    private DateTimer treeSetTimer, vectorTimer, doneTimer;
    @BeforeEach
    void setUp() {
        // Generate vector of 1, 2, 3 integers
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vectorTimer = new DateTimer(vector);

        // The same for the TreeSet
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(3);
        treeSet.add(7);
        treeSetTimer = new DateTimer(treeSet);

        // Done timer
        Vector<Integer> doneVector = new Vector<>();
        doneTimer = new DateTimer(doneVector);
    }

    @Test
    void constructorsTest() {
        Vector<Integer> values = new Vector<>(Arrays.asList(-1, null));

        // Test both constructors with negative values or null
        for (Integer value : values) {
            Vector<Integer> errorVector = new Vector<>(Arrays.asList(1, value));
            TreeSet<Integer> errorTreeSet = new TreeSet<>(Arrays.asList(1, value));
            assertThrows(IllegalArgumentException.class, () -> new DateTimer(errorVector));
            assertThrows(IllegalArgumentException.class, () -> new DateTimer(errorTreeSet));
        }

        // Test both constructors with non-ascending values
        Vector<Integer> nonAscendingVector = new Vector<>(Arrays.asList(1, 2, 1));
        TreeSet<Integer> nonAscendingTreeSet = new TreeSet<>(Arrays.asList(1, 2, 1));
        assertThrows(IllegalArgumentException.class, () -> new DateTimer(nonAscendingVector));
        assertThrows(IllegalArgumentException.class, () -> new DateTimer(nonAscendingTreeSet));
    }

    @Test
    void hasNext() {
        assertTrue(vectorTimer.hasNext());
        assertTrue(treeSetTimer.hasNext());
        assertFalse(doneTimer.hasNext());
    }

    @Test
    void next() {
        for (int i = 1; i < 4; i++) {
            assertEquals(i, vectorTimer.next());
            assertEquals(i, treeSetTimer.next());
        }
        assertThrows(NoSuchElementException.class, () -> doneTimer.next());
        assertThrows(NoSuchElementException.class, () -> vectorTimer.next());
    }
}