import BusinessLogic.BlContacts;
import Interfaces.IBlContacts;
import Interfaces.IContact;
import Model.Address;
import Model.Contact;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * Created by baez on 29.05.14.
 */
public class TestBlContacts extends TestCase {

    /**
     * Logger um JUnit-Testergebnisse in Logfile anzuzeigen
     */
    private static Logger logger = Logger.getLogger(IBlContacts.class.getName());

    public void setUp()
    {
        //TODO setup something
    }

    public void testBlContacts()
    {
        Contact contact = new Contact("Test","User","test@mail.de",LocalDate.now());
        Address adress = new Address();
        adress.setCity("TestStreet");
        adress.setStreetAddress("Testweg 21");
        adress.setZipCode("89322");
        contact.setAddress(adress);


        String city = contact.getAddress().getCity();
        String street = contact.getAddress().getStreetAddress();
        String zipCode = contact.getAddress().getZipCode();
        String houseNumber = contact.getAddress().getStreetAddress().replaceAll("[a-zA-Z]*","");

        String temp = houseNumber;

        BlContacts cont = new BlContacts();
        cont.createContactInDB(contact);



        logger.info("Contacte erfolgreich getestet");
        //TODO test something with DB
    }

}
