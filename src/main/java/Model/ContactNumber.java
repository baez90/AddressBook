package Model;

/**
 * #
 * Repräsentiert Rufnummer eines Kontakts
 *
 * @author baez
 */
public class ContactNumber {
    NumberType Type;
    String Number;

    /**
     * Standard-Konstruktor
     *
     * @param type   Typ der Rufnummer
     * @param number Rufnummer
     */
    public ContactNumber(NumberType type, String number) {
        Type = type;
        Number = number;
    }
}
