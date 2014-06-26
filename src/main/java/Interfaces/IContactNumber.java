package Interfaces;

import Model.ContactNumberType;
import javafx.beans.property.ObjectProperty;
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
     * Getter für den Typ als ObjectProperty für die TableView
     *
     * @return ContactNumberType als ObjectProperty
     */
    public ObjectProperty<ContactNumberType> getTypeProperty();

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

    /**
     * Getter für die Nummer als StringProperty für die TableView
     *
     * @return Nummer als StringProperty
     */
    public StringProperty getNumberProperty();

    /**
     * Getter für die ContactNumberID
     *
     * @return ContactNumberID als int
     */
    public int getContactNumbersID();

    /**
     * Setter für die ContactNumbersID
     *
     * @param id ID der Nummer als int
     */
    public void setContactNumbersID(int id);
}
