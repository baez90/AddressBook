import Interfaces.IContactNumberList;
import Model.Contact;
import Model.ContactList;
import Model.ContactNumber;
import Model.ContactNumberType;
import javafx.beans.property.SimpleStringProperty;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.Collections;
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
        Heinz = new Contact(new SimpleStringProperty("Karl"), new SimpleStringProperty("Heinz"), new SimpleStringProperty("heinz.hat@keineAdresse.de"), LocalDate.of(1985, 2, 21));
        IContactNumberList temp = Heinz.getContactNumbers();
        temp.add(new ContactNumber(ContactNumberType.Home, "1234"));
        temp.add(new ContactNumber(ContactNumberType.Work, "5678"));
        temp.add(new ContactNumber(ContactNumberType.Home, "91011"));
        temp.add(new ContactNumber(ContactNumberType.Mobile, "1213"));
        Heinz.setContactNumbers(temp);
        Ludwig = new Contact(new SimpleStringProperty("Ludwig"), new SimpleStringProperty("Eder"), new SimpleStringProperty("ludwig.eder@keineAdresse.de"), LocalDate.of(1987, 1, 22));
        Hans = new Contact(new SimpleStringProperty("Hans"), new SimpleStringProperty("Eicher"), new SimpleStringProperty("hans.eicher@keineAdresse.de"), LocalDate.of(1970, 8, 30));
    }

    public void testModel() {
        ContactList contactList = new ContactList();
        contactList.add(Heinz);
        contactList.add(Ludwig);
        contactList.add(Hans);

        Collections.sort(contactList);
        //TODO Contact-Sorierung testen
        //TODO Contact-Suche testen


        assertEquals(Heinz.getLastName(), "Heinz");
        assertEquals(Ludwig.getLastName(), "Eder");
        assertEquals(Hans.getLastName(), "Eicher");

        logger.info("Konstruktor erfolgreich getestet");

        Ludwig.setLastName(new SimpleStringProperty("Kaiser"));
        assertEquals(Ludwig.getLastName(), "Kaiser");

        logger.info("Setter getestet");
    }
}
