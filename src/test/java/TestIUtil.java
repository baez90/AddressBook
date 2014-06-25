import Interfaces.IUtil;
import junit.framework.TestCase;

import java.util.logging.Logger;

/**
 * Testet die Funktionen von IUtil
 *
 * @author baez
 */
public class TestIUtil extends TestCase {

    /**
     * Logger um über Erfolg der Tests informieren zu können
     */
    private static Logger logger = Logger.getLogger(IUtil.class.getName());

    String street1 = "Kaenguruallee 3g";
    String street2 = "Blumenstr. 36";
    String street3 = "Keine Strasse 852";

    String mailAddress1 = "nicht.lustig@boring.com";
    String mailAddress2 = "mail@mail@geh.oder.com";
    String mailAddress3 = "com.lach@mal.wieder.laut";

    /**
     * Testet extractStreet()-Methode
     */
    public void testExtractStreet() {
        assertEquals(IUtil.extractStreet(street1), "Kaenguruallee");
        assertEquals(IUtil.extractStreet(street2), "Blumenstr.");
        assertEquals(IUtil.extractStreet(street3), "Keine Strasse");

        logger.info("extractStreet erfolgreich getestet");
    }

    /**
     * Testet validateMailAddress()-Methode
     */
    public void testValidateMailAddress() {
        assertTrue(IUtil.validateMailAddress(mailAddress1));
        assertFalse(IUtil.validateMailAddress(mailAddress2));
        assertFalse(IUtil.validateMailAddress(mailAddress3));

        logger.info("validateMailAddress erfolgreich getestet");
    }
}
