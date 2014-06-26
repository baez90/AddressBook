package Interfaces;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * @author baez
 */
public interface IContact extends Comparable<IContact> {
    /**
     * Prüft ob Kontakt heute Geburtstag hat
     *
     * @return boolean ob Kontakt heute Geburtstag hat
     */
    public boolean dateOfBirthIsToday();

    /**
     * Standard-Getter für ContactID
     *
     * @return ID des Kontakts in der DB
     */
    public int getContactID();

    /**
     * Setter für die ID des Kontakts
     *
     * @param id ID des Kontakts als int
     */
    public void setContactID(int id);

    /**
     * Standard-Getter für Nachname des Kontakts
     *
     * @return Nachname des Kontakts
     */
    public String getLastName();

    /**
     * Standard-Setter für Nachname
     *
     * @param lastName Nachname des Kontakts
     */
    public void setLastName(String lastName);

    /**
     * Getter für TableView
     *
     * @return LastName als StringProperty
     */
    public StringProperty getLastNameProperty();

    /**
     * Standard-Getter für FirstName
     *
     * @return Vorname des Kontakts
     */
    public String getFirstName();

    /**
     * Standard-Setter für Vorname
     *
     * @param firstName setzt Vornamen des Kontakts
     */
    public void setFirstName(String firstName);

    /**
     * Getter für TableView
     *
     * @return FirstName als StringProperty
     */
    public StringProperty getFirstNameProperty();

    /**
     * Standard-Getter für Email-Adresse
     *
     * @return Email-Adresse des Kontakts
     */
    public String getMailAddress();

    /**
     * Standard-Setter für Email-Adresse
     *
     * @param mailAddress Email-Adresse des Kontakts
     */
    public void setMailAddress(String mailAddress);

    /**
     * Getter für TableView
     *
     * @return MailAddress als StringProperty
     */
    public StringProperty getMailAddressProperty();

    /**
     * Standard-Getter für die Rufnummern des Kontakts
     *
     * @return Liste aller Rufnummern des Kontakts
     */
    public IContactNumberList getContactNumbers();

    /**
     * Standard-Setter für die Rufnummern des Kontakts
     *
     * @param contactNumbers neue Liste der Rufnummern
     */
    public void setContactNumbers(IContactNumberList contactNumbers);

    /**
     * Standard-Getter für Geburtsdatum des Kontakts
     *
     * @return Geburtsdatum als LocalDate
     */
    public LocalDate getBirthDate();

    /**
     * Standard-Setter für Geburtstag des Kontakts
     *
     * @param birthDate Geburtstag als LocalDate
     */
    public void setBirthDate(LocalDate birthDate);

    /**
     * Getter für die TableView
     *
     * @return Birthday als Property
     */
    public ObjectProperty<LocalDate> getBirthdayProperty();

    /**
     * Standard-Getter für die Adresse des Kontakts
     *
     * @return Adres-Objekt des Kontakts
     */
    public IAddress getAddress();

    /**
     * Standard-Setter für die Adresse des Kontakts
     *
     * @param address neues Adress-Objekt für den Kontakt
     */
    public void setAddress(IAddress address);

    /**
     * benötigt für Birthday-Notification
     *
     * @return Vorname + Nachname als String
     */
    public String toString();
}
