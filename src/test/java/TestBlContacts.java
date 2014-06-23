import BusinessLogic.BlContacts;
import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;
import Interfaces.IErrorLog;
import Model.Address;
import Model.Contact;
import Model.ContactList;
import junit.framework.TestCase;

import java.io.File;
import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * Created by baez on 29.05.14.
 */
public class TestBlContacts extends TestCase {

    /**
     * Logger um JUnit-Testergebnisse in Logfile anzuzeigen
     */
    private static Logger logger = Logger.getLogger(IBlContacts.class.getName());
    private IContactList contactList = new ContactList();
    private String testPath = System.getProperty("user.home") + System.getProperty("file.separator") + "test.db";


    public void setUp() {
        Contact contact = new Contact("Test", "User", "test@mail.de", LocalDate.now());
        Address adress = new Address();
        adress.setCity("Rosenheim");
        adress.setStreetAddress("Testweg 21a");
        adress.setZipCode("89322");
        contact.setAddress(adress);
        Contact contact1 = new Contact("Dumbel", "User", "test@mail.de", LocalDate.now());
        Address adress1 = new Address();
        adress1.setCity("Dödeldorf");
        adress1.setStreetAddress("Döddelweg 30");
        adress1.setZipCode("89322");
        contact1.setAddress(adress1);
        Contact contact2 = new Contact("User", "Demp", "test@mail.de", LocalDate.now());
        Address adress2 = new Address();
        adress2.setCity("Dummdorf");
        adress2.setStreetAddress("Veilchenweg 12");
        adress2.setZipCode("89322");
        contact2.setAddress(adress2);
        Contact contact3 = new Contact("döddel", "User", "test@muuhh.de", LocalDate.now());
        Address adress3 = new Address();
        adress3.setCity("Testcity");
        adress3.setStreetAddress("Testweg 21a");
        adress3.setZipCode("89322");
        contact3.setAddress(adress3);

        contactList.add(contact);
        contactList.add(contact1);
        contactList.add(contact2);
        contactList.add(contact3);


    }

    public void testInitDB() {
        BlContacts cont = new BlContacts();
        cont.setDbPath(testPath);
        assertTrue(cont.initDB());
        logger.info("INIT DB getestet");
        if (!new File(testPath).delete()) {
            logger.info("Fehler beim löschen der TempDB");
        }

    }


    public void testBLCreateGetContacts() {
        BlContacts cont = new BlContacts();
        cont.setDbPath(testPath);
        cont.initDB();
        try {
            for (IContact c : contactList) {
                cont.createContactInDB(c);
            }

            IContactList testList = cont.getContactsFromDB();

            assertEquals(contactList.size(), testList.size());
            logger.info(String.valueOf(testList.size()) + " Kontakte in Datenbank");
        } catch (Exception e) {
            IErrorLog.saveError("BlContacts", "Fehler beim Lesen aller kontakte", e.toString());
        }
        if (!new File(testPath).delete()) {
            logger.info("Fehler beim löschen der TempDB");
        }

    }
}

