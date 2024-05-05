package discreteBehaviorSimulator;

import action.DiscreteAction;
import action.DiscreteActionInterface;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.OneShotTimer;
import timer.PeriodicTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class DiscreteActionSimulatorTest {

    private DiscreteActionSimulator simulator;

    @Before
    public void setUp() {
        simulator = new DiscreteActionSimulator();
    }

    @Test
    public void testConstructor() {
        //no getter in class
//        // Test logger configuration
//        assertNotNull("Logger should not be null", simulator.logger);
//        assertNotNull("FileHandler should not be null", simulator.logFile);
//        assertNotNull("ConsoleHandler should not be null", simulator.logConsole);
//
//        // Test if globalTime instance is properly defined
//        assertNotNull("Global time instance should not be null", simulator.globalTime);
    }

    @Test
    public void testAddActionSingleInstance() {
        // Créer une instance de DiscreteAction
        DiscreteAction action = new DiscreteAction();

        // Ajouter l'action au simulateur
        simulator.addAction(action);

        // Vérifier si l'action a été ajoutée à la liste
        assertTrue(simulator.actionsList.contains(action));
    }

    @Test
    public void testAddActionMultipleInstances() {
        // Créer plusieurs instances de DiscreteAction
        DiscreteAction action1 = new DiscreteAction();
        DiscreteAction action2 = new DiscreteAction();
        DiscreteAction action3 = new DiscreteAction();

        // Ajouter les actions au simulateur
        simulator.addAction(action3);
        simulator.addAction(action1);
        simulator.addAction(action2);

        // Vérifier si les actions sont triées dans la collection
        assertEquals(action1, simulator.actionsList.get(0));
        assertEquals(action2, simulator.actionsList.get(1));
        assertEquals(action3, simulator.actionsList.get(2));
    }

//    @Test
//    public void testUpdateTimesSingleAction() {
//        // Créer une instance de DiscreteAction avec un laps time
//        DiscreteAction action = new DiscreteAction();
//        action.setLapsTime(10); // laps time arbitraire
//
//        // Ajouter l'action au simulateur
//        simulator.addAction(action);
//
//        // Appeler updateTimes avec un runningTime arbitraire
//        simulator.updateTimes(5); // runningTime arbitraire
//
//        // Vérifier si le temps de l'action a été correctement réduit
//        assertEquals(5, action.getLapsTime());
//    }
//
//    @Test
//    public void testUpdateTimesMultipleActions() {
//        // Créer plusieurs instances de DiscreteAction avec des laps times différents
//        DiscreteAction action1 = new DiscreteAction();
//        action1.setLapsTime(10); // laps time arbitraire
//        DiscreteAction action2 = new DiscreteAction();
//        action2.setLapsTime(5); // laps time arbitraire
//        DiscreteAction action3 = new DiscreteAction();
//        action3.setLapsTime(8); // laps time arbitraire
//
//        // Ajouter les actions au simulateur
//        simulator.addAction(action1);
//        simulator.addAction(action2);
//        simulator.addAction(action3);
//
//        // Appeler updateTimes avec un runningTime arbitraire
//        simulator.updateTimes(5); // runningTime arbitraire
//
//        // Vérifier si le temps de chaque action a été correctement réduit
//        assertEquals(5, action1.getLapsTime());
//        assertEquals(0, action2.getLapsTime());
//        assertEquals(3, action3.getLapsTime());
//    }
//
//    @Test
//    public void testUpdateTimesMultipleActionsWithSameLapsTime() {
//        // Créer plusieurs instances de DiscreteAction avec le même laps time
//        DiscreteAction action1 = new DiscreteAction();
//        action1.setLapsTime(5); // laps time arbitraire
//        DiscreteAction action2 = new DiscreteAction();
//        action2.setLapsTime(5); // laps time arbitraire
//
//        // Ajouter les actions au simulateur
//        simulator.addAction(action1);
//        simulator.addAction(action2);
//
//        // Appeler updateTimes avec un runningTime arbitraire
//        simulator.updateTimes(5); // runningTime arbitraire
//
//        // Vérifier si une seule action a été mise à jour
//        assertEquals(1, simulator.actionsList.size());
//    }
}