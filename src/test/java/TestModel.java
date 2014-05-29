import Model.Contact;
import Model.ContactList;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * Created by baez on 29.05.14.
 */
public class TestModel extends TestCase {
    private static Logger logger = Logger.getLogger(Contact.class.getName());
    private Contact Heinz;
    private Contact Ludwig;
    private Contact Hans;

    public void setUp() {
        Heinz = new Contact("Karl", "Heinz", "heinz.hat@keineAdresse.de", LocalDate.of(15, 2, 1985));
        Ludwig = new Contact("Ludwig", "Eder", "ludwig.eder@keineAdresse.de", LocalDate.of(22, 1, 1987));
        Hans = new Contact("Hans", "Eicher", "hans.eicher@keineAdresse.de", LocalDate.of(30, 8, 1970));
    }

    public void testModel() {
        ContactList contactList = new ContactList();
        contactList.add(Heinz);
        contactList.add(Ludwig);
        contactList.add(Hans);

        assertEquals(Heinz.getName(), "Heinz");
        assertEquals(Ludwig.getName(), "Eder");
        assertEquals(Hans.getName(), "Eicher");

        logger.info("Konstruktor erfolgreich getestet");

        Ludwig.setName("Kaiser");
        assertEquals(Ludwig.getName(), "Kaiser");

        logger.info("Setter getestet");
    }
}
