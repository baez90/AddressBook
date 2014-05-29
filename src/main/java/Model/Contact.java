package Model;

import Interfaces.IContact;
import Interfaces.IContactNumberList;

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
    private String FirstName;
    /**
     * Nachname des Kontakts
     */
    private String Name;
    /**
     * Email-Adresse des Kontakts
     */
    private String MailAddress;
    /**
     * Geburtstag des Kontakts
     */
    private LocalDate BirthDate;
    /**
     * Liste der Rufnummern für einen Kontakt
     */
    private IContactNumberList ContactNumbers;

    /**
     * Konstruktor für CreateContact
     * kann direkt mit allen Attributen befüllt werden
     *
     * @param firstName   Vorname des Kontakts
     * @param name        Nachname des Kontakts
     * @param mailAddress Email-Adresse des Kontakts
     */
    public Contact(String firstName, String name, String mailAddress, LocalDate birthDate) {
        ContactID = 0;
        FirstName = firstName;
        Name = name;
        MailAddress = mailAddress;
        BirthDate = birthDate;
        ContactNumbers = new ContactNumberList();
    }

    /**
     * Konstruktor für auslesen aus der DB
     *
     * @param id          ID des Kontakts in der DB
     * @param firstName   Vorname des Kontakts
     * @param name        Nachname des Kontakts
     * @param mailAddress Email-Adresse des Kontakts
     */
    public Contact(int id, String firstName, String name, String mailAddress, LocalDate birthDate) {
        ContactID = id;
        FirstName = firstName;
        Name = name;
        MailAddress = mailAddress;
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

    public String getFirstName() {
        return FirstName;
    }

    /**
     * Standard-Setter für Vorname
     *
     * @param firstName setzt Vornamen des Kontakts
     */

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    /**
     * Standard-Getter für Nachname des Kontakts
     *
     * @return Nachname des Kontakts
     */
    public String getName() {
        return Name;
    }

    /**
     * Standard-Setter für Nachname
     *
     * @param name Nachname des Kontakts
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Standard-Getter für Email-Adresse
     *
     * @return Email-Adresse des Kontakts
     */
    public String getMailAddress() {
        return MailAddress;
    }

    /**
     * Standard-Setter für Email-Adresse
     *
     * @param mailAddress Email-Adresse des Kontakts
     */
    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
    }

    /**
     * Standard-Getter für die Rufnummern des Kontakts
     *
     * @return Liste aller Rufnummern des Kontakts
     */
    public IContactNumberList getContactNumbers() {
        return ContactNumbers;
    }

    /**
     * Standard-Setter für die Rufnummern des Kontakts
     *
     * @param contactNumbers neue Liste der Rufnummern
     */
    public void setContactNumbers(ContactNumberList contactNumbers) {
        ContactNumbers = contactNumbers;
    }

    /**
     * Standard-Getter für Geburtsdatum des Kontakts
     *
     * @return Geburtsdatum als LocalDate
     */
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

    @Override
    public int compareTo(IContact o) {
        return 0;
    }

    @Override
    public boolean dateOfBirthIsToday() {
        //TODO prüfen ob heute Geburtstag
        return false;
    }
}
