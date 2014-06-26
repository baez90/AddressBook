package Model;

import Interfaces.IContactNumber;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Repräsentiert Rufnummer eines Kontakts
 *
 * @author baez
 */
public class ContactNumber implements IContactNumber {
    /**
     * ID der Nummer in der DB
     */
    private int ContactNumbersID;
    /**
     * Typ der Nummer
     */
    private ContactNumberType Type;
    /**
     * Nummer als String
     */
    private StringProperty Number;

    /**
     * Standard-Konstruktor
     *
     * @param type   Typ der Rufnummer
     * @param number Rufnummer
     */
    public ContactNumber(ContactNumberType type, String number) {
        ContactNumbersID = 0;
        Type = type;
        Number = new SimpleStringProperty(number);
    }

    /**
     * Konstruktor für BlContacts
     *
     * @param id     ID der Nummer
     * @param type   Typ der Rufnummer
     * @param number Rufnummer als String
     */
    public ContactNumber(int id, ContactNumberType type, String number) {
        ContactNumbersID = id;
        Type = type;
        Number = new SimpleStringProperty(number);
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
     * Getter für den Typ als ObjectProperty für die TableView
     *
     * @return ContactNumberType als ObjectProperty
     */
    @Override
    public ObjectProperty<ContactNumberType> getTypeProperty() {
        return new SimpleObjectProperty<>(Type);
    }

    /**
     * Standard-Getter
     *
     * @return Rufnummer als String
     */
    @Override
    public String getNumber() {
        return Number.getValue();
    }

    /**
     * Standard-Setter
     *
     * @param number neue Rufnummer als String
     */
    @Override
    public void setNumber(String number) {
        Number.setValue(number);
    }

    /**
     * Getter für die Nummer als StringProperty für die TableView
     *
     * @return Nummer als StringProperty
     */
    @Override
    public StringProperty getNumberProperty() {
        return Number;
    }

    /**
     * Getter für die ContactNumberID
     *
     * @return ContactNumberID als int
     */
    @Override
    public int getContactNumbersID() {
        return ContactNumbersID;
    }

    /**
     * Setter für die ContactNumbersID
     *
     * @param id ID der Nummer als int
     */
    @Override
    public void setContactNumbersID(int id) {
        ContactNumbersID = id;
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
        if (o == null) {
            return 1;
        }
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

    /**
     * Benötigt für Contains
     *
     * @param o Object welches verglichen werden soll
     * @return boolsches Ergebnis des Vergleichs
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactNumber that = (ContactNumber) o;

        if (!Number.getValue().equals(that.Number.getValue())) return false;
        if (Type != that.Type) return false;

        return true;
    }
}
