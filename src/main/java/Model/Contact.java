package Model;

import Interfaces.IContact;

import java.time.LocalDate;
import java.util.List;

/**
 * Repräsentiert Kontakt in der Datenbank
 *
 * @author baez
 */
public class Contact implements IContact, Comparable {

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
    private NumberList ContactNumbers;

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
        ContactNumbers = new NumberList();
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
        ContactNumbers = new NumberList();
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
    public List<ContactNumber> getContactNumbers() {
        return ContactNumbers;
    }

    /**
     * Standard-Setter für die Rufnummern des Kontakts
     *
     * @param contactNumbers neue Liste der Rufnummern
     */
    public void setContactNumbers(NumberList contactNumbers) {
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

    /**
     * Überschreibt Kontakt in der Datenbank
     *
     * @return int-Wert zur Repräsentation des Erfolgs
     */
    @Override
    public int updateContactInDB() {
        //TODO Kontakt in DB updaten, Liste von Telefonnummern berücksichtigen
        return 0;
    }

    /**
     * Entfernt Kontakt aus der Datenbank
     *
     * @return int-Wert zur Repräsentation des Erfolgs
     */
    @Override
    public int removeContactInDB() {
        //TODO Kontakt aus der DB löschen, Fehlercode berücksichtigen
        return 0;
    }

    /**
     * legt Kontakt in der Datenbank an
     *
     * @return int-Wert zur Repräsentation des Erfolgs
     */
    @Override
    public int createContactInDB() {
        //TODO Kontakt in DB anlegen, Fehlercode berücksichtigen
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        //TODO implement compareTo
        return 0;
    }
}
