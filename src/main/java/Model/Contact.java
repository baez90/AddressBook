package Model;

/**
 * Created by baez on 29.05.14.
 */
public class Contact implements IContact {

    private int ContactID;
    private String FirstName;
    private String Name;
    private String CellNr;
    private String PhoneNr;
    private String MailAddress;

    /**
     * Konstruktor für CreateContact
     * kann direkt mit allen Attributen befüllt werden
     * @param firstName Vorname des Kontakts
     * @param name Nachname des Kontakts
     * @param cellNr Handy-Nr. des Kontakts
     * @param phoneNr Festnetz-Rufnummer des Kontakts
     * @param mailAddress Email-Adresse des Kontakts
     */
    public Contact(String firstName, String name, String cellNr, String phoneNr, String mailAddress) {
        ContactID = 0;
        FirstName = firstName;
        Name = name;
        CellNr = cellNr;
        PhoneNr = phoneNr;
        MailAddress = mailAddress;
    }

    /**
     * Konstruktor für auslesen aus der DB
     * @param id
     * @param firstName
     * @param name
     * @param cellNr
     * @param phoneNr
     * @param mailAddress
     */
    public Contact(int id, String firstName, String name, String cellNr, String phoneNr, String mailAddress) {
        ContactID = id;
        FirstName = firstName;
        Name = name;
        CellNr = cellNr;
        PhoneNr = phoneNr;
        MailAddress = mailAddress;
    }
    public int getContactID() {
        return ContactID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCellNr() {
        return CellNr;
    }

    public void setCellNr(String cellNr) {
        CellNr = cellNr;
    }

    public String getPhoneNr() {
        return PhoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        PhoneNr = phoneNr;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
    }

    @Override
    public int updateContactInDB() {
        //TODO
        return 0;
    }

    @Override
    public int removeContactInDB() {
        //TODO
        return 0;
    }

    @Override
    public int createContactInDB() {
        //TODO
        return 0;
    }
}
