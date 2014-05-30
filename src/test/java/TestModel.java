import Model.Contact;
import Model.ContactList;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * Testet das Model
 *
 * @author Baez
 */
public class TestModel extends TestCase {
    /**
     * Logger um JUnit-Testergebnisse in Logfile anzuzeigen
     */
    private static Logger logger = Logger.getLogger(Contact.class.getName());
    private Contact Heinz;
    private Contact Ludwig;
    private Contact Hans;

    public void setUp() {
        Heinz = new Contact("Karl", "Heinz", "heinz.hat@keineAdresse.de", LocalDate.of(1985, 2, 21));
        Ludwig = new Contact("Ludwig", "Eder", "ludwig.eder@keineAdresse.de", LocalDate.of(1987, 1, 22));
        Hans = new Contact("Hans", "Eicher", "hans.eicher@keineAdresse.de", LocalDate.of(1970, 8, 30));
    }

    public void testModel() {
        ContactList contactList = new ContactList();
        contactList.add(Heinz);
        contactList.add(Ludwig);
        contactList.add(Hans);

        //TODO Contact-Suche testen
        //TODO Contact-Sorierung testen

        assertEquals(Heinz.getName(), "Heinz");
        assertEquals(Ludwig.getName(), "Eder");
        assertEquals(Hans.getName(), "Eicher");

        logger.info("Konstruktor erfolgreich getestet");

        Ludwig.setName("Kaiser");
        assertEquals(Ludwig.getName(), "Kaiser");

        logger.info("Setter getestet");
    }
}
