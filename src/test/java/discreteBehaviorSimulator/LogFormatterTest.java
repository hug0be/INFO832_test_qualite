package discreteBehaviorSimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatterTest {

    @Test
    public void testFormatWithValidLogRecord() {
        LogRecord record = new LogRecord(Level.INFO, "Test message");
        record.setMillis(1631524132000L); // Arbitrarily chosen milliseconds for the date
        LogFormatter formatter = new LogFormatter();

        String formattedString = formatter.format(record);

        assertEquals("Formatage incorrect du LogRecord", "2021.09.13 12:28:52.000: INFO\nTest message\n", formattedString);
    }

    @Test
    public void testFormatWithEmptyLogRecord() {
        LogRecord record = new LogRecord(Level.INFO, "");
        LogFormatter formatter = new LogFormatter();

        String formattedString = formatter.format(record);

        assertEquals("Le formatage d'un LogRecord vide devrait être vide", "\n\n", formattedString);
    }

    @Test
    public void testCalcDateWithValidMillis() {
        long millisecs = 1631524132000L; // Arbitrarily chosen milliseconds for the date
        LogFormatter formatter = new LogFormatter();

        String formattedDate = formatter.calcDate(millisecs);

        assertEquals("Calcul incorrect de la date", "2021.09.13 12:28:52.000", formattedDate);
    }

    @Test
    public void testCalcDateWithNegativeMillis() {
        long millisecs = -1631524132000L; // Negative milliseconds
        LogFormatter formatter = new LogFormatter();

        String formattedDate = formatter.calcDate(millisecs);

        assertNull("Les millisecondes négatives ne devraient pas être converties en date", formattedDate);
    }
}
