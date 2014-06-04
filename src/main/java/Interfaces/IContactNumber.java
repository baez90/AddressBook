package Interfaces;

import Model.ContactNumberType;

/**
 * Created by baez on 29.05.14.
 */
public interface IContactNumber extends Comparable<IContactNumber> {
    /**
     * Standard-Getter
     *
     * @return Typ als ContactNumberType Enum
     */
    public ContactNumberType getType();

    /**
     * Standard-Setter
     *
     * @param type neuer Typ der Nummer als ContactNumberType
     */
    public void setType(ContactNumberType type);

    /**
     * Standard-Getter
     *
     * @return Rufnummer als String
     */
    public String getNumber();

    /**
     * Standard-Setter
     *
     * @param number neue Rufnummer als String
     */
    public void setNumber(String number);
}
