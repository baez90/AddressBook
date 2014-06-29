import BusinessLogic.BlContacts;
import BusinessLogic.ErrorLog;
import Interfaces.*;
import Model.*;
import Model.Error;
import junit.framework.TestCase;

import java.io.File;
import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * @author baez
 */
public class TestBlContacts extends TestCase {

    /**
     * Logger um JUnit-Testergebnisse in Logfile anzuzeigen
     */
    private static Logger logger = Logger.getLogger(IBlContacts.class.getName());
    IContactNumberList numberList = new ContactNumberList();
    private IContactList contactList = new ContactList();
    private String testPath = System.getProperty("user.home") + System.getProperty("file.separator");

    /**
     * Füllt eine Liste von Kontakten für den Test
     */
    public void setUp() {

        numberList.add(new ContactNumber(ContactNumberType.Home, "7896541"));
        numberList.add(new ContactNumber(ContactNumberType.Mobile, "8563241"));
        numberList.add(new ContactNumber(ContactNumberType.Work, "85236945"));

        Contact contact = new Contact("Test", "User", "test@mail.de", LocalDate.now());
        contact.setContactNumbers(numberList);
        Address adress = new Address();
        adress.setCity("Rosenheim");
        adress.setStreetAddress("Testweg 21a");
        adress.setZipCode("89322");
        contact.setAddress(adress);
        Contact contact1 = new Contact("Dumbel", "User", "test@mail.de", LocalDate.now());
        contact1.setContactNumbers(numberList);
        Address adress1 = new Address();
        adress1.setCity("Dödeldorf");
        adress1.setStreetAddress("Döddelweg 30");
        adress1.setZipCode("89322");
        contact1.setAddress(adress1);
        Contact contact2 = new Contact("User", "Demp", "test@mail.de", LocalDate.now());
        contact2.setContactNumbers(numberList);
        Address adress2 = new Address();
        adress2.setCity("Dummdorf");
        adress2.setStreetAddress("Veilchenweg 12");
        adress2.setZipCode("89322");
        contact2.setAddress(adress2);
        Contact contact3 = new Contact("döddel", "User", "test@muuhh.de", LocalDate.now());
        contact3.setContactNumbers(numberList);
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

    /**
     * Testet die createContactInDB() und die getContactsFromDB() Methoden
     */
    public void testBLCreateGetContacts() {
        IBlContacts createBl = new BlContacts();
        createBl.setDbPath(testPath + "CreateGet.db");
        createBl.initDB();
        try {
            contactList.forEach(createBl::createContactInDB);
            IContactList testList = createBl.getContactsFromDB();
            assertEquals(contactList.size(), testList.size());
            logger.info(String.valueOf(testList.size()) + " Kontakte in Datenbank");
            testList.forEach(c -> c.getContactNumbers().forEach(n -> assertTrue(numberList.contains(n))));
            logger.info("ContactNumbers erfolgreich getestet");
        } catch (Exception e) {
            ErrorLog.getInstance().saveError(new Error("TestBlContacts", "testCreateGetContacts", "", e.toString(), e.getStackTrace()));

        }
        deleteTempDb(testPath + "CreateGet.db");

    }

    /**
     * Testet die removeContactInDB() und die getContactsFromDB() Methoden
     */
    public void testRemoveGetContacts() {
        IBlContacts removeBl = new BlContacts();
        IContactList removeContactList = new ContactList();
        contactList.forEach(removeContactList::add);
        removeBl.setDbPath(testPath + "RemoveGet.db");
        removeBl.initDB();
        removeContactList.forEach(c -> {
            c.setContactID(removeBl.createContactInDB(c));
            if (c.getContactID() < 1) {
                logger.info("Fehler beim erstellen eines Kontakts in testRemoveGetContacts");
                deleteTempDb(testPath + "RemoveGet.db");
                fail();
            }
        });
        if (removeBl.removeContactInDB(removeContactList.get(0)) != 1) {
            logger.info("Kontakt konnte nicht gelöscht werden");
            deleteTempDb(testPath + "RemoveGet.db");
            fail();
        }
        IContactList resultList = removeBl.getContactsFromDB();
        assertTrue(!resultList.contains(contactList.get(0)));
        logger.info("RemovegetContacts erfolgreich getestet");
        deleteTempDb(testPath + "RemoveGet.db");
    }

    /**
     * Testet die updateContactInDB() und die getContactsFromDB() Methoden
     */
    public void testUpdateContacts() {
        IBlContacts updateBl = new BlContacts();
        IContactList resultList;
        updateBl.setDbPath(testPath + "UpdateContacts.db");
        updateBl.initDB();
        boolean updateSuccess = false;
        IContact testContact = new Contact("Ted", "Tester", "ted.tester@test.com", LocalDate.now());
        testContact.setContactID(updateBl.createContactInDB(testContact));

        testContact.setFirstName("Teddy");
        updateBl.updateContactInDB(testContact);

        resultList = updateBl.getContactsFromDB();
        for (IContact c : resultList) {
            if (c.getFirstName().equals("Teddy")) {
                updateSuccess = true;
            }
        }
        assertTrue(updateSuccess);
        logger.info("updateContacts erfolgreich getestet");
        deleteTempDb(testPath + "UpdateContacts.db");
    }

    public void testNumberManagement() {
        IBlContacts createNumberBl = new BlContacts();
        createNumberBl.setDbPath(testPath + "CreateNumber.db");
        createNumberBl.initDB();

        contactList.forEach(createNumberBl::createContactInDB);

        IContactList resultList = createNumberBl.getContactsFromDB();
        IContactNumber testNumber = new ContactNumber(ContactNumberType.Mobile, "123456");
        resultList.get(0).getContactNumbers().add(testNumber);
        createNumberBl.updateContactInDB(resultList.get(0));
        resultList = createNumberBl.getContactsFromDB();
        assertTrue(resultList.get(0).getContactNumbers().contains(testNumber));
        logger.info("CreateNumber durch Contact-Update erfolgreich getestet");

        resultList.get(0).getContactNumbers().get(0).setNumber("456389");
        createNumberBl.updateContactInDB(resultList.get(0));
        resultList = createNumberBl.getContactsFromDB();
        assertTrue(resultList.get(0).getContactNumbers().get(0).getNumber().equals("456389"));
        logger.info("Update von Nummer erfolgreich getestet");

        testNumber = resultList.get(0).getContactNumbers().remove(0);
        resultList.get(0).getContactNumbers().remove(testNumber);
        createNumberBl.updateContactInDB(resultList.get(0));
        resultList = createNumberBl.getContactsFromDB();
        assertFalse(resultList.get(0).getContactNumbers().contains(testNumber));
        logger.info("Entfernen von Nummer erfolgreich getestet");

        deleteTempDb(testPath + "CreateNumber.db");
    }


    /**
     * Hilfsmethode zum ablöschen der diversten TempDBs
     *
     * @param path Pfad zur TempDB welche gelöscht werden soll
     */
    private void deleteTempDb(String path) {
        if (!new File(path).delete()) {
            logger.info("Fehler beim löschen der TempDB " + path);
            fail();
        }
    }
}

