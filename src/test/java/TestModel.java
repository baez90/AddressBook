import Interfaces.IContactList;
import Interfaces.IContactNumberList;
import Model.Contact;
import Model.ContactList;
import Model.ContactNumber;
import Model.ContactNumberType;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        IContactNumberList temp = Heinz.getContactNumbers();
        temp.add(new ContactNumber(ContactNumberType.Home, "1234"));
        temp.add(new ContactNumber(ContactNumberType.Work, "5678"));
        temp.add(new ContactNumber(ContactNumberType.Home, "91011"));
        temp.add(new ContactNumber(ContactNumberType.Mobile, "1213"));
        Heinz.setContactNumbers(temp);
        Ludwig = new Contact("Ludwig", "Eder", "ludwig.eder@keineAdresse.de", LocalDate.of(1987, 1, 22));
        Hans = new Contact("Hans", "Eicher", "hans.eicher@keineAdresse.de", LocalDate.of(1970, 8, 30));
    }

    public void testModel() {
        IContactList contactList = new ContactList();
        contactList.add(Heinz);
        contactList.add(Ludwig);
        contactList.add(Hans);

        IContactList cloneList = contactList.stream().sorted().collect(Collectors.toCollection(ContactList::new));
        for (int i = 0; i < cloneList.size(); i++) {
            assertEquals(contactList.get(i).getLastName(), cloneList.get(i).getLastName());
        }
        logger.info("Sortierung geprüft");


        IContactList heinzResult = contactList.searchContacts("HeInz");
        assertEquals(Heinz.getLastName(), heinzResult.get(0).getLastName());
        IContactList ludwigResult = contactList.searchContacts("lUdwi");
        assertEquals(Ludwig.getLastName(), ludwigResult.get(0).getLastName());
        IContactList hansResult = contactList.searchContacts("hANs");
        assertEquals(Hans.getLastName(), hansResult.get(0).getLastName());
        IContactList heinzNrResult = contactList.searchContacts("1011");
        assertEquals(Heinz.getLastName(), heinzNrResult.get(0).getLastName());
        logger.info("Suche getestet");


        assertEquals(Heinz.getLastName(), "Heinz");
        assertEquals(Ludwig.getLastName(), "Eder");
        assertEquals(Hans.getLastName(), "Eicher");

        logger.info("Konstruktor erfolgreich getestet");

        Ludwig.setLastName("Kaiser");
        assertEquals(Ludwig.getLastName(), "Kaiser");

        logger.info("Setter getestet");
    }
}
