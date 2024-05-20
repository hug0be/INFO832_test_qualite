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
    private DiscreteAction action1, action2, action3, action4;
    private DiscreteActionCycle action, actionAlmostDone, actionWithDependencies;

    @BeforeEach
    void setUp() {
        // Création des objets
        dummyObject1 = new DummyObject();
        dummyObject2 = new DummyObject();

        // Création des dépendances
        action1 = new DiscreteAction(dummyObject1, "A", new PeriodicTimer(30));
        action2 = new DiscreteAction(dummyObject1, "A", new OneShotTimer(30));
        action3 = new DiscreteAction(dummyObject1, "A", new PeriodicTimer(30));
        action4 = new DiscreteAction(dummyObject2, "B", new OneShotTimer(15));

        // Création des actions dépendantes
        action = new DiscreteActionCycle(action1);
        actionAlmostDone = new DiscreteActionCycle(action2);

        actionWithDependencies = new DiscreteActionCycle(action3);
        actionWithDependencies.addDependence(action4);
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
        assertEquals(2, actionWithDependencies.actions.size());

        // Check valid dependence addition
        actionWithDependencies.addDependence(new DiscreteAction(dummyObject2, "B", new PeriodicTimer(10)));
        assertEquals(3, actionWithDependencies.actions.size());

        // Check same dependence addition
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(action2));
        assertEquals(3, actionWithDependencies.actions.size());

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
        // Check invalid arguments : null timer
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(
                new DiscreteAction(dummyObject2, "A", null)
        ));
        // Check invalid arguments : null object
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.addDependence(
                new DiscreteAction(null, "A", new OneShotTimer(10))
        ));
    }

    @Test
    void spendTime() {
        // Check 0 argument
        action.spendTime(0);
        assertEquals(30, action.getCurrentLapsTime());

        // Check negative and null arguments
        assertThrows(IllegalArgumentException.class, () -> actionWithDependencies.spendTime(-10));

        // Check if action is unchanged when timer is not expired
        actionWithDependencies.spendTime(10);
        assertEquals(20, actionWithDependencies.getCurrentLapsTime());
        assertEquals(action3, actionWithDependencies.currentAction);

        // Check if actions loop back when actions are expired
        actionWithDependencies.next();
        assertEquals(action4, actionWithDependencies.currentAction);
        actionWithDependencies.spendTime(20);

        // Also check if the time is correctly updated
        assertEquals(action3, actionWithDependencies.currentAction);
        assertEquals(20, actionWithDependencies.getCurrentLapsTime());

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

        // .. but when actionWithDependencies loops back, action has less time left
        actionWithDependencies.next();
        assertEquals(1, actionWithDependencies.compareTo(action));
    }

    @Test
    void next() {
        // Check initial action
        assertEquals(action3, actionWithDependencies.currentAction);
        assertEquals(30, actionWithDependencies.getCurrentLapsTime());

        // Check if actions increment
        actionWithDependencies.next();
        assertEquals(action4, actionWithDependencies.currentAction);
        assertEquals(15, actionWithDependencies.getCurrentLapsTime());

        // Check if actions loops back
        actionWithDependencies.next();
        assertEquals(action3, actionWithDependencies.currentAction);
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