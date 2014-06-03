package Model;

import Interfaces.IContact;
import Interfaces.IContactNumberList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Repräsentiert Kontakt in der Datenbank
 *
 * @author baez
 */
public class Contact implements IContact {

    /**
     * ID des Kontakts in der Datenbank
     */
    private int ContactID;
    /**
     * Vorname des Kontakts
     */
    private StringProperty FirstName;
    /**
     * Nachname des Kontakts
     */
    private StringProperty LastName;
    /**
     * Email-Adresse des Kontakts
     */
    private StringProperty MailAddress;
    /**
     * Geburtstag des Kontakts
     */
    private LocalDate BirthDate;
    private Address Address;

    /**
     * Liste der Rufnummern für einen Kontakt
     */
    private IContactNumberList ContactNumbers;

    /**
     * Konstruktor für CreateContact
     * kann direkt mit allen Attributen befüllt werden
     *
     * @param firstName   Vorname des Kontakts
     * @param lastName    Nachname des Kontakts
     * @param mailAddress Email-Adresse des Kontakts
     */
    public Contact(String firstName, String lastName, String mailAddress, LocalDate birthDate) {
        ContactID = 0;
        FirstName = new SimpleStringProperty(firstName);
        LastName = new SimpleStringProperty(lastName);
        MailAddress = new SimpleStringProperty(mailAddress);
        BirthDate = birthDate;
        ContactNumbers = new ContactNumberList();
    }

    /**
     * Konstruktor für auslesen aus der DB
     *
     * @param id          ID des Kontakts in der DB
     * @param firstName   Vorname des Kontakts
     * @param lastName    Nachname des Kontakts
     * @param mailAddress Email-Adresse des Kontakts
     */
    public Contact(int id, String firstName, String lastName, String mailAddress, LocalDate birthDate) {
        ContactID = id;
        FirstName = new SimpleStringProperty(firstName);
        LastName = new SimpleStringProperty(lastName);
        MailAddress = new SimpleStringProperty(mailAddress);
        BirthDate = birthDate;
        ContactNumbers = new ContactNumberList();
    }

    /**
     * Standard-Getter für ContactID
     *
     * @return ID des Kontakts in der DB
     */
    public int getContactID() {
        return ContactID;
    }

    /**
     * Standard-Getter für FirstName
     *
     * @return Vorname des Kontakts
     */
    @Override
    public String getFirstName() {
        return FirstName.getValue();
    }

    /**
     * Standard-Setter für Vorname
     *
     * @param firstName setzt Vornamen des Kontakts
     */

    public void setFirstName(String firstName) {
        FirstName = new SimpleStringProperty(firstName);
    }

    /**
     * Standard-Getter für Nachname des Kontakts
     *
     * @return Nachname des Kontakts
     */
    @Override
    public String getLastName() {
        return LastName.getValue();
    }

    /**
     * Standard-Setter für Nachname
     *
     * @param lastName Nachname des Kontakts
     */
    public void setLastName(String lastName) {
        LastName = new SimpleStringProperty(lastName);
    }

    /**
     * Standard-Getter für Email-Adresse
     *
     * @return Email-Adresse des Kontakts
     */
    @Override
    public String getMailAddress() {
        return MailAddress.getValue();
    }

    /**
     * Standard-Setter für Email-Adresse
     *
     * @param mailAddress Email-Adresse des Kontakts
     */
    public void setMailAddress(String mailAddress) {
        MailAddress = new SimpleStringProperty(mailAddress);
    }

    /**
     * Standard-Getter für die Rufnummern des Kontakts
     *
     * @return Liste aller Rufnummern des Kontakts
     */
    @Override
    public IContactNumberList getContactNumbers() {
        return ContactNumbers;
    }

    /**
     * Standard-Setter für die Rufnummern des Kontakts
     *
     * @param contactNumbers neue Liste der Rufnummern
     */
    public void setContactNumbers(IContactNumberList contactNumbers) {
        ContactNumbers = contactNumbers;
    }

    /**
     * Standard-Getter für Geburtsdatum des Kontakts
     *
     * @return Geburtsdatum als LocalDate
     */
    @Override
    public LocalDate getBirthDate() {
        return BirthDate;
    }

    /**
     * Standard-Setter für Geburtstag des Kontakts
     *
     * @param birthDate Geburtstag als LocalDate
     */
    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }

    public Address getAddress() {
        return Address;
    }

    public void setAddress(Address address) {
        Address = address;
    }

    @Override
    public int compareTo(IContact o) {
        return (LastName + " " + FirstName).compareTo(o.getLastName() + " " + o.getFirstName());
    }

    @Override
    public boolean dateOfBirthIsToday() {
        return LocalDate.now().equals(BirthDate);
    }
}
