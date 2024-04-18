package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.OneShotTimer;
import timer.PeriodicTimer;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionDependentTest {

    public static class DummyObject {
        public Integer A() { return 1; }
        public Integer B() { return 2; }
    }

    // Objet en commun avec toutes les actions
    private final DummyObject dummyObject = new DummyObject();
    private final DummyObject dependentObject1 = new DummyObject();
    private final DummyObject dependentObject2 = new DummyObject();
    private final DummyObject dependentObject3 = new DummyObject();

    // Déclaration des différentes actions
    private DiscreteActionDependent action, actionAlmostDone, actionWithDependences;

    @BeforeEach
    void setUp() {
        action = new DiscreteActionDependent(
            dummyObject, "A", new PeriodicTimer(30)
        );
        actionAlmostDone = new DiscreteActionDependent(
            dummyObject, "A", new OneShotTimer(30)
        );
        actionWithDependences = new DiscreteActionDependent(
            dummyObject, "A", new PeriodicTimer(30)
        );
        actionWithDependences.addDependence(dependentObject1, "B", new PeriodicTimer(10));
        actionWithDependences.addDependence(dependentObject1, "B", new OneShotTimer(10));

        actionAlmostDone.addDependence(dependentObject1, "B", new OneShotTimer(10));
    }

    @Test
    void constructorsTest() throws NoSuchMethodException {
        for (DiscreteActionDependent _action: new ArrayList<>(Arrays.asList(action, actionAlmostDone, actionWithDependences))) {
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
        assertEquals(2, actionWithDependences.depedentActions.size());

        // Check valid dependence addition
        actionWithDependences.addDependence(dependentObject3, "B", new PeriodicTimer(10));
        assertEquals(3, actionWithDependences.depedentActions.size());

        // Check same dependence addition
        actionWithDependences.addDependence(dependentObject3, "B", new PeriodicTimer(10));
        assertEquals(4, actionWithDependences.depedentActions.size());

        // Check invalid arguments : method that does not exist
        assertThrows(NoSuchMethodException.class, () -> { actionWithDependences.addDependence(dependentObject3, "thisMethodDoesntExist", new OneShotTimer(10)); });
        // Check invalid arguments : empty method name
        assertThrows(IllegalArgumentException.class, () -> { actionWithDependences.addDependence(dependentObject3, "", new OneShotTimer(10)); });
        // Check invalid arguments : null method name
        assertThrows(IllegalArgumentException.class, () -> { actionWithDependences.addDependence(dependentObject3, null, new OneShotTimer(10)); });
        // Check invalid arguments : null object
        assertThrows(IllegalArgumentException.class, () -> { actionWithDependences.addDependence(null, "A", new OneShotTimer(10)); });
        // Check invalid arguments : null timer
        assertThrows(IllegalArgumentException.class, () -> { actionWithDependences.addDependence(dependentObject3, "A", null); });
    }

    @Test
    void nextMethod() {
        // Check actions with no dependent actions
        assertThrows(NoSuchElementException.class, () -> { action.nextMethod(); });

        // Check initial currentAction
        assertEquals(actionWithDependences.baseAction, actionWithDependences.currentAction);

        // Check if actions increment
        actionWithDependences.nextMethod();
        assertEquals(actionWithDependences.depedentActions.first(), actionWithDependences.currentAction);

        // Check if actions loop back
        actionWithDependences.nextMethod();
        actionWithDependences.nextMethod();
        assertEquals(actionWithDependences.baseAction, actionWithDependences.currentAction);
    }

    @Test
    void spendTime() {
        // Check invalid arguments (negatives and max integers)
//        assertThrows(InvalidParameterException.class, () -> { actionWithDependences.spendTime(-10); });
        // TODO : Ajouter test pour max integer

        // Check if action is unchanged when timer is not expired
        actionWithDependences.spendTime(10);
        assertEquals(20, actionWithDependences.getCurrentLapsTime());
        assertEquals(actionWithDependences.baseAction, actionWithDependences.currentAction);

        // Check if action was changed when timer expires
        actionWithDependences.spendTime(20);
        assertEquals(actionWithDependences.depedentActions.first(), actionWithDependences.currentAction);
        assertEquals(10, actionWithDependences.getCurrentLapsTime());

        // Check if actions loop back when all dependent actions are expired
        actionWithDependences.spendTime(20);
        assertEquals(actionWithDependences.baseAction, actionWithDependences.currentAction);
    }

    @Test
    void compareTo() {
        // action and actionDependences have the same time left
        assertEquals(0, action.compareTo(actionWithDependences));

        // action has less time left than actionDependences it spends 10 units of time
        action.spendTime(10);
        assertEquals(-1, action.compareTo(actionWithDependences));

        // .. but when actionDependences changes action, action has more time left
        actionWithDependences.nextMethod();
        assertEquals(1, action.compareTo(actionWithDependences));
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
        assertEquals(actionWithDependences.depedentActions.first(), actionWithDependences.currentAction);
        assertEquals(10, actionWithDependences.getCurrentLapsTime());

        // Check if actions loop back
        actionWithDependences.next();
        assertEquals(actionWithDependences.baseAction, actionWithDependences.currentAction);
        assertEquals(30, actionWithDependences.getCurrentLapsTime());

        // Check exception when action is done
        actionAlmostDone.next();
        assertThrows(NoSuchElementException.class, actionAlmostDone::next);
    }

    @Test
    void hasNext() {
        assertTrue(actionAlmostDone.hasNext());
        actionAlmostDone.next();
        assertFalse(actionAlmostDone.hasNext());
    }
}