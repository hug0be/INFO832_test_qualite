package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.OneShotTimer;
import timer.PeriodicTimer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionToggleTest {
    public static class DummyObject {
        public Integer A() { return 1; }
        public Integer B() { return 2; }
    }

    // Objet en commun avec toutes les actions
    private final DummyObject dummyObject = new DummyObject();

    // Déclaration des différentes actions
    private DiscreteActionToggle action, almostDoneAction, treeSetAction, onFirstAction, onFirstTreeSetAction;

    @BeforeEach
    void setUp() {
        action = new DiscreteActionToggle(
            dummyObject, "A", new PeriodicTimer(30),  "B", new PeriodicTimer(10)
        );
        almostDoneAction = new DiscreteActionToggle(
            dummyObject, "A", new OneShotTimer(30), "B", new OneShotTimer(10)
        );
        treeSetAction = new DiscreteActionToggle(
            dummyObject, "A", new TreeSet<>(Arrays.asList(30, 40)), "B", new TreeSet<>(Arrays.asList(10, 35))
        );
        onFirstAction = new DiscreteActionToggle(
            dummyObject, "A", new OneShotTimer(10),  "B", new OneShotTimer(30)
        );
        onFirstTreeSetAction = new DiscreteActionToggle(
            dummyObject, "A", new TreeSet<>(List.of(10)),  "B", new TreeSet<>(List.of(30))
        );
    }

    @Test
    void constructorsUsualActionsTest() throws NoSuchMethodException {
        for (DiscreteActionToggle _action: new ArrayList<>(Arrays.asList(treeSetAction, action))) {
            // Action par défaut doit être off
            assertEquals(_action.offAction, _action.currentAction);
            // Les attributs des actions sont bien initialisés
            assertEquals(dummyObject, _action.onAction.getObject());
            assertEquals(dummyObject, _action.getObject());
            assertEquals(dummyObject.getClass().getDeclaredMethod("A"), _action.onAction.getMethod());
            assertEquals(dummyObject.getClass().getDeclaredMethod("B"), _action.getMethod());
            // Les timers sont bien initialisés
            assertEquals(10, _action.getCurrentLapsTime());
        }
    }

    @Test
    void constructorsOnFirstActionsTest() {
        for (DiscreteActionToggle _action: new ArrayList<>(Arrays.asList(onFirstTreeSetAction, onFirstAction))) {
            // Action par défaut doit être on, car "on" se produit avant "off"
            assertEquals(_action.onAction, _action.currentAction);
            // Les timers sont bien initialisés
            assertEquals(30, _action.getCurrentLapsTime());
        }
    }

    @Test
    void onToOffNext() {
        onFirstAction.next();
        assertEquals(onFirstAction.offAction, onFirstAction.currentAction);
        assertEquals(10, onFirstAction.getCurrentLapsTime());
    }

    @Test
    void offToOnNext() {
        action.next();
        assertEquals(action.onAction, action.currentAction);
        assertEquals(20, action.getCurrentLapsTime());
    }

    @Test
    void doneActionHasNoNext() {
        almostDoneAction.next().next();
        assertFalse(almostDoneAction.hasNext());
        assertEquals(-1, almostDoneAction.getCurrentLapsTime());
        // On lance un autre next en le testant
        assertThrows(NoSuchElementException.class, almostDoneAction::next);
        // Puisqu'on a lancé 'next', il ne doit plus y avoir d'action à faire
        assertNull(almostDoneAction.currentAction);
    }

    @Test
    void actionHasOneNext() {
        almostDoneAction.next();
        assertTrue(action.hasNext());
    }

    @Test
    void actionHasNext() {
        action.next().next();
        assertTrue(action.hasNext());
    }

    @Test
    void treeSetActionHasNoNext() {
        treeSetAction.next().next().next();
        assertTrue(treeSetAction.hasNext());
        treeSetAction.next();
        assertFalse(treeSetAction.hasNext());
    }

    @Test
    void compareToTest() {
        assertEquals(0, action.compareTo(almostDoneAction));
        assertEquals(-1, action.compareTo(onFirstAction));
        // On termine 'almostDoneAction'
        almostDoneAction.next().next();
        assertEquals(1, action.compareTo(almostDoneAction));
    }

    @Test
    void spendTimeTest() {
        action.spendTime(2);
        assertEquals(8, action.getCurrentLapsTime());
        action.spendTime(13);
        assertEquals(action.onAction, action.currentAction);
        assertEquals(15, action.getCurrentLapsTime());
    }
}