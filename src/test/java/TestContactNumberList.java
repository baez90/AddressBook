import Interfaces.IContactNumber;
import Interfaces.IContactNumberList;
import Model.ContactNumber;
import Model.ContactNumberList;
import Model.ContactNumberType;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Prüft die Funktionen der ContactNumberList
 *
 * @author baez
 */
public class TestContactNumberList extends TestCase {

    /**
     * Logger um JUnit-Testergebnisse in Logfile anzuzeigen
     */
    private static Logger logger = Logger.getLogger(IContactNumberList.class.getName());
    private IContactNumberList numberList = new ContactNumberList();
    private IContactNumberList homeNumbers;
    private IContactNumberList workNumbers;
    private IContactNumberList mobileNumbers;

    /**
     * Demo-Daten initialisieren
     */
    public void setUp() {
        numberList.add(new ContactNumber(ContactNumberType.Home, "1234"));
        numberList.add(new ContactNumber(ContactNumberType.Work, "5678"));
        numberList.add(new ContactNumber(ContactNumberType.Home, "9876"));
        numberList.add(new ContactNumber(ContactNumberType.Mobile, "5432"));
        numberList.add(new ContactNumber(ContactNumberType.Mobile, "1987"));
        numberList.add(new ContactNumber(ContactNumberType.Work, "4536"));
        numberList.add(new ContactNumber(ContactNumberType.Home, "8523"));

        homeNumbers = numberList.getNumbersByType(ContactNumberType.Home);
        workNumbers = numberList.getNumbersByType(ContactNumberType.Work);
        mobileNumbers = numberList.getNumbersByType(ContactNumberType.Mobile);
    }

    /**
     * Tests durchführen
     */
    public void testNumberList() {
        /*
        prüft ob die GetNumberByType Methode ihren Zweck erfüllt
         */
        for (IContactNumber ic : homeNumbers) {
            assertEquals(ContactNumberType.Home, ic.getType());
        }
        for (IContactNumber ic : workNumbers) {
            assertEquals(ContactNumberType.Work, ic.getType());
        }
        for (IContactNumber ic : mobileNumbers) {
            assertEquals(ContactNumberType.Mobile, ic.getType());
        }

        logger.info("GetNumberByType erfolgreicht getestet");

        /*
        Prüft ob das gruppierte einfügen der Elemente funktioniert
         */
        ContactNumberType lastType = null;
        List<ContactNumberType> typeHistory = new LinkedList<>();

        for (IContactNumber ic : numberList) {
            if (lastType == null) {
                lastType = ic.getType();
                typeHistory.add(ic.getType());
                continue;
            }

            if (!ic.getType().equals(lastType) && !typeHistory.contains(ic.getType())) {
                lastType = ic.getType();
                typeHistory.add(ic.getType());
                continue;
            }
            if (!ic.getType().equals(lastType) && typeHistory.contains(ic.getType())) {
                fail();
            }
        }
        logger.info("Gruppiertes einfügen erfolgreich getestet");
    }
}
