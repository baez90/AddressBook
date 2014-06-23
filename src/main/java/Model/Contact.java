package Model;

import Interfaces.IAddress;
import Interfaces.IContact;
import Interfaces.IContactNumberList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private ObjectProperty<LocalDate> BirthDate;
    /**
     * Adresse des Kontakts
     */
    private IAddress Address;

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
        BirthDate = new SimpleObjectProperty<>(birthDate);
        ContactNumbers = new ContactNumberList();
        Address = new Address();
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
        BirthDate = new SimpleObjectProperty<>(birthDate);
        ContactNumbers = new ContactNumberList();
        Address = new Address();
    }

    /**
     * Standard-Getter für ContactID
     *
     * @return ID des Kontakts in der DB
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
        return BirthDate.getValue();
    }

    /**
     * Standard-Setter für Geburtstag des Kontakts
     *
     * @param birthDate Geburtstag als LocalDate
     */
    @Override
    public void setBirthDate(LocalDate birthDate) {
        BirthDate.set(birthDate);
    }

    /**
     * Standard-Getter für die Adresse des Kontakts
     *
     * @return Adres-Objekt des Kontakts
     */
    @Override
    public IAddress getAddress() {
        return Address;
    }

    /**
     * Standard-Setter für die Adresse des Kontakts
     *
     * @param address neues Adress-Objekt für den Kontakt
     */
    @Override
    public void setAddress(IAddress address) {
        Address = address;
    }

    /**
     * Notwendige Methode zur Anzeige in der Tabelle
     *
     * @return BirthDay-Property
     */
    public ObjectProperty<LocalDate> birthdayProperty() {
        return BirthDate;
    }

    /**
     * Obligatorische Implementierung für das Compare-Interface
     *
     * @param o IContact-Objekt welches verglichen werden soll
     * @return 1 falls this größer ist, 0 falls gleich, -1 falls o größer ist
     */
    @Override
    public int compareTo(IContact o) {
        return LastName.getValue().compareTo(o.getLastName());
    }

    /**
     * Prüft ob Kontakt heute Geburtstag hat
     *
     * @return boolean ob Kontakt heute Geburtstag hat
     */
    @Override
    public boolean dateOfBirthIsToday() {
        LocalDate today = LocalDate.now();
        return BirthDate.getValue().getDayOfMonth() == today.getDayOfMonth() && BirthDate.getValue().getMonth().equals(today.getMonth());
    }

    /**
     * Obligatorische equals-Methode
     * Vergleich Vor- und Nachnamen
     *
     * @param o Objekt welches verglichen werden soll
     * @return boolean ob die 2 Objekte gleich sind
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Contact)) {
            return false;
        } else {
            IContact con = (Contact) o;
            return FirstName.getValue().equals(con.getFirstName()) && LastName.getValue().equals(con.getLastName());
        }

    }

    @Override
    public String toString() {

        return getFirstName() + " " + getLastName();
    }
}
