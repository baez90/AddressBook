import Model.Contact;
import Model.ContactList;
import junit.framework.TestCase;

/**
 * Created by baez on 29.05.14.
 */
public class TestModel extends TestCase {

    public void TestModel() {
        Contact Heinz = new Contact("Karl", "Heinz", "heinz.hat@keineAdresse.de");
        Contact Ludwig = new Contact("Ludwig", "Eder", "ludwig.eder@keineAdresse.de");
        Contact Hans = new Contact("Hans", "Eicher", "hans.eicher@keineAdresse.de");

        ContactList contactList = new ContactList();
        contactList.add(Heinz);
        contactList.add(Ludwig);
        contactList.add(Hans);

        assertEquals(Heinz.getName(), "Heinz");
        assertEquals(Ludwig.getName(), "Eder");
        assertEquals(Hans.getName(), "Eicher");

        Ludwig.setName("Kaiser");
        assertEquals(Ludwig.getName(), "Kaiser");
    }
}
