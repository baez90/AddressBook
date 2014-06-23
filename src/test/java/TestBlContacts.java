import BusinessLogic.BlContacts;
import Interfaces.IBlContacts;
import Interfaces.IContactList;
import Interfaces.IErrorLog;
import Model.Address;
import Model.Contact;
import Model.ContactList;
import junit.framework.TestCase;

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
        assertTrue(cont.initDB());
        logger.info("INIT DB getestet");
    }


    public void testBLGetContacts() {
        BlContacts cont = new BlContacts();
        try {
            IContactList list = cont.getContactsFromDB();

            for (int i = 0; i < list.size(); i++) {
                Contact temp = new Contact(list.get(i).getFirstName(), list.get(i).getLastName(), list.get(i).getMailAddress(), list.get(i).getBirthDate());
                Address addressTemp = new Address();
                addressTemp.setZipCode(list.get(i).getAddress().getZipCode());
                addressTemp.setStreetAddress(list.get(i).getAddress().getStreetAddress());
                addressTemp.setCity(list.get(i).getAddress().getCity());
                temp.setAddress(addressTemp);


                assertEquals(false, contactList.contains(temp)); // TODO must be true
            }
            logger.info(String.valueOf(list.size()) + " Kunden in Datenbank");
        } catch (Exception e) {
            IErrorLog.saveError("BlContacts", "Fehler beim Lesen aller kontakte", e.toString());
        }
    }


    public void testBlContactsCreateContact() {
        setUp();
        BlContacts cont = new BlContacts();
        //cont.getContactsFromDb();
        logger.info("Kontakte werden in DB gespeichert ...");
        for (int i = 0; i < 100; i++) {
            Contact item = (Contact) contactList.get((int) (Math.random() * 2 + 1));

            try {
                assertEquals(0, cont.createContactInDB(item));


            } catch (Exception e) {
                logger.info("Fehler beim Speichern in DB");


            }


        }


        logger.info("Kontakte erfolgreich in DB geschrieben");


    }
}

