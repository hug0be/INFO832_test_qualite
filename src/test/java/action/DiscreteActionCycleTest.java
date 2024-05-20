package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.OneShotTimer;
import timer.PeriodicTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionCycleTest {

    public static class DummyObject {
        public Integer A() { return 1; }
        public Integer B() { return 2; }
    }

    // Objet en commun avec toutes les actions
    private final DummyObject dummyObject = new DummyObject();
    private final DummyObject dependentObject1 = new DummyObject();
    private final DummyObject dependentObject2 = new DummyObject();

    // Déclaration des différentes actions
    private DiscreteActionCycle action, actionAlmostDone, actionWithDependences;

    @BeforeEach
    void setUp() {
        action = new DiscreteActionCycle(
            dummyObject, "A", new PeriodicTimer(30)
        );
        actionAlmostDone = new DiscreteActionCycle(
            dummyObject, "A", new OneShotTimer(30)
        );
        actionWithDependences = new DiscreteActionCycle(
            dummyObject, "A", new PeriodicTimer(30)
        );
        actionWithDependences.addDependence(dependentObject1, "B", new PeriodicTimer(10));
        actionWithDependences.addDependence(dependentObject2, "B", new OneShotTimer(10));
    }

    @Test
    void constructorsTest() throws NoSuchMethodException {
        for (DiscreteActionCycle _action: new ArrayList<>(Arrays.asList(action, actionAlmostDone, actionWithDependences))) {
            // Les attributs des actions sont bien initialisés
            assertEquals(dummyObject, _action.getObject());
            assertEquals(dummyObject.getClass().getDeclaredMethod("A"), _action.getMethod());
            // Les timers sont bien initialisés
            assertEquals(30, _action.getCurrentLapsTime());
        }
    }
    @Test
    void addDependence() {
        // Check initial state
        assertEquals(2, actionWithDependences.otherActions.size());

        // Check valid dependence addition
        actionWithDependences.addDependence(dependentObject2, "A", new PeriodicTimer(10));
        assertEquals(3, actionWithDependences.otherActions.size());

        // Check same dependence addition
        assertThrows(IllegalArgumentException.class, () -> actionWithDependences.addDependence(dependentObject1, "B", new PeriodicTimer(10)));
        assertEquals(3, actionWithDependences.otherActions.size());

        // Check invalid arguments : method that does not exist
        assertThrows(IllegalArgumentException.class, () -> actionWithDependences.addDependence(dependentObject2, "thisMethodDoesntExist", new OneShotTimer(10)));
        // Check invalid arguments : empty method name
        assertThrows(IllegalArgumentException.class, () -> actionWithDependences.addDependence(dependentObject2, "", new OneShotTimer(10)));
        // Check invalid arguments : null method name
        assertThrows(IllegalArgumentException.class, () -> actionWithDependences.addDependence(dependentObject2, null, new OneShotTimer(10)));
        // Check invalid arguments : null object
        assertThrows(IllegalArgumentException.class, () -> actionWithDependences.addDependence(null, "A", new OneShotTimer(10)));
        // Check invalid arguments : null timer
        assertThrows(IllegalArgumentException.class, () -> actionWithDependences.addDependence(dependentObject2, "A", null));
    }

    @Test
    void nextMethod() {
        // Check actions with no dependent actions
        assertThrows(NoSuchElementException.class, () -> action.nextMethod());

        // Check if actions increment
        actionWithDependences.nextMethod();
        assertEquals(actionWithDependences.otherActions.first(), actionWithDependences.currentAction);
    }

    @Test
    void spendTime() {
        // Check invalid arguments (negatives and max integers)
        action.spendTime(0);
        assertEquals(30, action.getCurrentLapsTime());

        // Check negative arguments
        assertThrows(IllegalArgumentException.class, () -> actionWithDependences.spendTime(-10));

        // Check if action is unchanged when timer is not expired
        actionWithDependences.spendTime(10);
        assertEquals(20, actionWithDependences.getCurrentLapsTime());
        assertEquals(actionWithDependences.firstAction, actionWithDependences.currentAction);

        // Check if action was changed when timer expires
        actionWithDependences.spendTime(20);
        assertEquals(actionWithDependences.otherActions.first(), actionWithDependences.currentAction);
        assertEquals(10, actionWithDependences.getCurrentLapsTime());

        // Check if actions loop back when all dependent actions are expired
        actionWithDependences.spendTime(20);
        assertEquals(actionWithDependences.firstAction, actionWithDependences.currentAction);

        // TODO : Ajouter test pour max integer
    }

    @Test
    void compareTo() {
        // action and actionDependences have the same time left
        assertEquals(0, actionWithDependences.compareTo(action));

        // action has less time left than actionDependences it spends 10 units of time
        action.spendTime(10);
        assertEquals(1, actionWithDependences.compareTo(action));

        // .. but when actionDependences changes action, action has more time left
        actionWithDependences.next();
        assertEquals(-1, actionWithDependences.compareTo(action));

        // .. but when action is done, actionDependences has more time left
        action.next();
        assertEquals(1, actionWithDependences.compareTo(action));
    }

    @Test
    void isEmpty() {
        assertTrue(action.isEmpty());
        assertFalse(actionWithDependences.isEmpty());
    }

    @Test
    void next() {
        // Check if actions increment
        actionWithDependences.next();
        assertEquals(actionWithDependences.otherActions.first(), actionWithDependences.currentAction);
        assertEquals(10, actionWithDependences.getCurrentLapsTime());

        // Check if actions loop back
        actionWithDependences.next().next();
        assertEquals(actionWithDependences.firstAction, actionWithDependences.currentAction);
        assertEquals(30, actionWithDependences.getCurrentLapsTime());

        // Check exception when action is done
        assertThrows(NoSuchElementException.class, actionAlmostDone::next);
    }

    @Test
    void hasNext() {
        assertTrue(action.hasNext());
        assertFalse(actionAlmostDone.hasNext());
    }
}