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

    /**
     * Standard-Getter
     *
     * @return Typ als ContactNumberType Enum
     */
    @Override
    public ContactNumberType getType() {
        return Type;
    }

    /**
     * Standard-Setter
     *
     * @param type neuer Typ der Nummer als ContactNumberType
     */
    @Override
    public void setType(ContactNumberType type) {
        Type = type;
    }

    /**
     * Standard-Getter
     *
     * @return Rufnummer als String
     */
    @Override
    public String getNumber() {
        return Number;
    }

    /**
     * Standard-Setter
     *
     * @param number neue Rufnummer als String
     */
    @Override
    public void setNumber(String number) {
        Number = number;
    }

    /**
     * obligatorische Methode für Comparable-Interface
     * Gruppiert die Nummern automatisch nach ihrem Typ
     *
     * @param o IContactNumber Objekt zum vergleichen
     * @return 1 falls this "größer", 0 falls gleich, -1 falls this kleiner
     */
    @Override
    public int compareTo(IContactNumber o) {
        if (Type.equals(o.getType())) {
            return 0;
        } else if (Type.equals(ContactNumberType.Mobile)) {
            return -2;
        } else if (Type == ContactNumberType.Home && o.getType().equals(ContactNumberType.Work)) {
            return -2;
        } else {
            return 1;
        }
    }
}
