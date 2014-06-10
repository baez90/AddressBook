package BusinessLogic;

import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;
import Interfaces.IErrorLog;

import java.sql.*;
import java.time.LocalDate;

/**
 * @author baez
 */
public class BlContacts implements IBlContacts {
    /**
     * Name des verwendeten Datenbank-Treibers
     */
    private String DriverName = "org.sqlite.JDBC";
    /**
     * Speichert den Pfad zur Datenbank-Datei ab
     */
    private String DbPath;

    /**
     * Kontakt in der DB updaten
     *
     * @param contact Kontakt welcher geupdatet werden soll
     * @return Erfolgs-Code
     */
    @Override
    public int updateContactInDB(IContact contact)
    {
        String city = contact.getAddress().getCity();
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String mailAdress = contact.getMailAddress();
        LocalDate date = contact.getBirthDate();
        String street = contact.getAddress().getStreetAddress();
        String zipCode = contact.getAddress().getZipCode();
        String houseNumber = contact.getAddress().getStreetAddress().replaceAll("[a-zA-Z]*","");

        String query = "Update Contacts Set FirstName = '" + firstName + "',LastName = '" +
                       lastName + "',MailAdress = '" + mailAdress + "',Street = '" +
                       street + "',HouseNumber = '" + houseNumber + "',ZipCode = " +
                       zipCode + "',City = '" + city + "',Birthdate = '" + date + "'" +
                       "where ContactID = " + contact.getContactID();

        ExecuteQuery(query);

        return 0;
    }

    @Override
    public int removeContactInDB(IContact contact) {

        int contactID = contact.getContactID();
        String query = "Delete From Contacts where ContactID = " + contactID;

        ExecuteQuery(query);
        return 0;
    }

    @Override
    public int createContactInDB(IContact contact)
    {
        String city = contact.getAddress().getCity();
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String mailAdress = contact.getMailAddress();
        LocalDate date = contact.getBirthDate();
        String street = contact.getAddress().getStreetAddress();
        String zipCode = contact.getAddress().getZipCode();
        String houseNumber = contact.getAddress().getStreetAddress().replaceAll("[a-zA-Z]*","");

        String query = "INSERT INTO Contacts Values('"+ firstName + "','"
                      + lastName + "','" + mailAdress + "','" + street +
                       "','" + houseNumber + "','" + zipCode + "','" + city + "','"
                       + date + "')";

        ExecuteQuery(query);


        return 0;
    }

    @Override
    public boolean initDB() {
        return false;
    }

    @Override
    public String getDbPath() {
        return DbPath;
    }

    @Override
    public void setDbPath(String dbPath) {
        DbPath = dbPath;
    }

    @Override
    public IContactList getContactsFromDB() {
        try {
            Class.forName(DriverName);
            Connection con = DriverManager.getConnection(DbPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void ExecuteQuery(String query)
    {
        Connection con = null;
        try
        {
            try
            {
                Class.forName(DriverName);
                con = DriverManager.getConnection(DbPath);
            } catch (ClassNotFoundException e)
            {
                IErrorLog.saveError("BLContacts", "Class NOT FOUND", e.toString());
            } catch (SQLException e)
            {
                IErrorLog.saveError("BLContacts", "SQL Fehler", e.toString());
            }

            Statement stmt = null;
            try
            {
                stmt = con.createStatement();
            } catch (SQLException e)
            {
                IErrorLog.saveError("BLContacts", "Fehler beim Erstellen des Statements", e.toString());
            }
            try
            {
                ResultSet rst = stmt.executeQuery(query);
            } catch (SQLException e)
            {
                IErrorLog.saveError("BLContacts", "Fehler beim ausführen des Querys", e.toString());
            }
        }
        catch (Exception e)
        {
            IErrorLog.saveError("BLContacts","Unbekannter Fehler aufgetreten",e.toString());
        }
        finally
        {
            if(con != null)
                try
                {
                    con.close();
                } catch (SQLException e)
                {
                    IErrorLog.saveError("BLContacts","Fehler beim Schließen der Verbindung",e.toString());
                }
        }

    }
}
