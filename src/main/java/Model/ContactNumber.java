package Model;

import Interfaces.IContactNumber;

/**
 * Repräsentiert Rufnummer eines Kontakts
 *
 * @author baez
 */
public class ContactNumber implements IContactNumber {
    private ContactNumberType Type;
    private String Number;

    /**
     * Standard-Konstruktor
     *
     * @param type   Typ der Rufnummer
     * @param number Rufnummer
     */
    public ContactNumber(ContactNumberType type, String number) {
        Type = type;
        Number = number;
    }

    @Override
    public ContactNumberType getType() {
        return Type;
    }

    @Override
    public void setType(ContactNumberType type) {
        Type = type;
    }

    @Override
    public String getNumber() {
        return Number;
    }

    @Override
    public void setNumber(String number) {
        Number = number;
    }

    @Override
    public int compareTo(IContactNumber o) {
        //TODO implement compareTo
        return 0;
    }
}
