package Interfaces;

import Model.ContactNumberType;
import javafx.beans.property.StringProperty;

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

    public StringProperty getNumberProperty();

    public int getContactNumbersID();

    public void setContactNumbersID(int id);
}
