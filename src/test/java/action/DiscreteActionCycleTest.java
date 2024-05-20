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
    private DummyObject dummyObject1, dummyObject2;

    // Déclaration des différentes actions (dépendances et non-dépendances)
    private DiscreteAction action1, action2, action3;
    private DiscreteActionCycle action, actionAlmostDone, actionWithDependencies;

    @BeforeEach
    void setUp() {
        // Création des objets
        dummyObject1 = new DummyObject();
        dummyObject2 = new DummyObject();

        // Création des dépendances
        action1 = new DiscreteAction(dummyObject1, "A", new PeriodicTimer(30));
        action2 = new DiscreteAction(dummyObject1, "A", new OneShotTimer(30));
        action3 = new DiscreteAction(dummyObject2, "B", new PeriodicTimer(30));

        // Création des actions dépendantes
        action = new DiscreteActionCycle(action1);
        actionAlmostDone = new DiscreteActionCycle(action2);

        actionWithDependencies = new DiscreteActionCycle(action1);
        actionWithDependencies.addDependence(action2);
    }

    @Test
    void constructorsTest() throws NoSuchMethodException {
        for (DiscreteActionCycle _action: new ArrayList<>(Arrays.asList(action, actionAlmostDone, actionWithDependencies))) {
            // Les attributs des actions sont bien initialisés
            assertEquals(dummyObject1, _action.getObject());
            assertEquals(dummyObject1.getClass().getDeclaredMethod("A"), _action.getMethod());
            // Les timers sont bien initialisés
            assertEquals(30, _action.getCurrentLapsTime());
        }
    }
    @Test
    void addDependence() {
        // Check initial state
        assertEquals(2, actionWithDependencies.otherActions.size());

        // Check valid dependence addition
        actionWithDependencies.addDependence(action3);
        assertEquals(3, actionWithDependencies.otherActions.size());

        // Check same dependence addition
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(action2));
        assertEquals(3, actionWithDependencies.otherActions.size());

        // Check invalid arguments : method that does not exist
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(
                new DiscreteAction(dummyObject2, "thisMethodDoesntExist", new OneShotTimer(10))
        ));
        // Check invalid arguments : empty method name
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(
                new DiscreteAction(dummyObject2, "", new OneShotTimer(10))
        ));
        // Check invalid arguments : null method name
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(
                new DiscreteAction(dummyObject2, null, new OneShotTimer(10))
        ));
        // Check invalid arguments : null object
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(
                new DiscreteAction(null, "A", new OneShotTimer(10))
        ));
        // Check invalid arguments : null timer
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(
                new DiscreteAction(dummyObject2, "A", null)
        ));
    }

    @Test
    void nextMethod() {
        // Check actions with no dependent actions
        assertThrows(NoSuchElementException.class, () -> action.next());

        // Check if actions increment
        actionWithDependencies.next();
        assertEquals(actionWithDependencies.otherActions.first(), actionWithDependencies.currentAction);
    }

    @Test
    void spendTime() {
        // Check invalid arguments (negatives and max integers)
        action.spendTime(0);
        assertEquals(30, action.getCurrentLapsTime());

        // Check negative arguments
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.spendTime(-10));

        // Check if action is unchanged when timer is not expired
        actionWithDependencies.spendTime(10);
        assertEquals(20, actionWithDependencies.getCurrentLapsTime());
        assertEquals(actionWithDependencies.firstAction, actionWithDependencies.currentAction);

        // Check if action was changed when timer expires
        actionWithDependencies.spendTime(20);
        assertEquals(actionWithDependencies.otherActions.first(), actionWithDependencies.currentAction);
        assertEquals(10, actionWithDependencies.getCurrentLapsTime());

        // Check if actions loop back when all dependent actions are expired
        actionWithDependencies.spendTime(20);
        assertEquals(actionWithDependencies.firstAction, actionWithDependencies.currentAction);

        // TODO : Ajouter test pour max integer
    }

    @Test
    void compareTo() {
        // action and actionDependences have the same time left
        assertEquals(0, actionWithDependencies.compareTo(action));

        // action has less time left than actionDependences it spends 10 units of time
        action.spendTime(10);
        assertEquals(1, actionWithDependencies.compareTo(action));

        // .. but when actionDependences changes action, action has more time left
        actionWithDependencies.next();
        assertEquals(-1, actionWithDependencies.compareTo(action));

        // .. but when action is done, actionDependences has more time left
        action.next();
        assertEquals(1, actionWithDependencies.compareTo(action));
    }

    @Test
    void isEmpty() {
        assertTrue(action.isEmpty());
        assertFalse(actionWithDependencies.isEmpty());
    }

    @Test
    void next() {
        // Check if actions increment
        actionWithDependencies.next();
        assertEquals(actionWithDependencies.otherActions.first(), actionWithDependencies.currentAction);
        assertEquals(10, actionWithDependencies.getCurrentLapsTime());

        // Check if actions loop back
        actionWithDependencies.next().next();
        assertEquals(actionWithDependencies.firstAction, actionWithDependencies.currentAction);
        assertEquals(30, actionWithDependencies.getCurrentLapsTime());

        // Check exception when action is done
        assertThrows(NoSuchElementException.class, actionAlmostDone::next);
    }

    @Test
    void hasNext() {
        assertTrue(action.hasNext());
        assertFalse(actionAlmostDone.hasNext());
    }
}