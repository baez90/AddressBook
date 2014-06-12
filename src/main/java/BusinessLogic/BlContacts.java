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
public class BlContacts implements IBlContacts
{
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
        String street = "";
        String zipCode = contact.getAddress().getZipCode();
        //String houseNumber = contact.getAddress().getStreetAddress().replaceAll("[a-zA-Z]*","");
        String houseNumber = contact.getAddress().getStreetAddress();

        for(int i = 0; i <= houseNumber.length();i++)
        {
            if(Character.isLetter(houseNumber.charAt(i)))
            {
                street += houseNumber.charAt(i);
            }
            else
                break;


        }
        houseNumber = houseNumber.substring(street.length(),houseNumber.length());

      /*  String query = "Update Contacts Set FirstName = '" + firstName + "',LastName = '" +
                       lastName + "',MailAdress = '" + mailAdress + "',Street = '" +
                       street + "',HouseNumber = '" + houseNumber + "',ZipCode = " +
                       zipCode + "',City = '" + city + "',Birthdate = '" + date + "'" +
                       "where ContactID = " + contact.getContactID();*/ // maybe useful ;)

        String query = String.format("Update Contacts Set FirstName = '%s',LastName = '%s',MailAddress = '%s'," +
                                     "Street = '%s',HouseNumber = '%d',ZipCode = '%d',City = '%s',BirthDate = '%d' " +
                                      "where ContactID = %d"
                                     ,firstName,lastName,mailAdress,street,houseNumber,zipCode,city,date,contact.getContactID());

        ExecuteQuery(query);

        return 0;
    }

    @Override
    public int removeContactInDB(IContact contact)
    {

        int contactID = contact.getContactID();
        String query = String.format("Delete From Contacts where ContactID = %d",contactID);

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
        String street = "";
        String zipCode = contact.getAddress().getZipCode();
        //String houseNumber = contact.getAddress().getStreetAddress().replaceAll("[a-zA-Z]*","");
        String houseNumber = contact.getAddress().getStreetAddress();

        for(int i = 0; i <= houseNumber.length();i++)
        {
            if(Character.isLetter(houseNumber.charAt(i)))
            {
                street += houseNumber.charAt(i);
            }
            else
                break;


        }
        houseNumber = houseNumber.substring(street.length(),houseNumber.length());


        /*String query = String.format("INSERT INTO Contacts Values('"+ firstName + "','"
                      + lastName + "','" + mailAdress + "','" + street +
                       "','" + houseNumber + "','" + zipCode + "','" + city + "','"
                       + date + "')");*/ // maybe useful

        String query = String.format("INSERT INTO Contacts Values('%s','%s','%s','%s','%d','%d','%s','%s'"
                ,firstName,lastName,mailAdress,street,houseNumber,zipCode,city,date);

        PreparedStatement stmt;

        ExecuteQuery(query);


        return 0;
    }

    @Override
    public boolean initDB()
    {
        return false;
    }

    @Override
    public String getDbPath()
    {
        return DbPath;
    }

    @Override
    public void setDbPath(String dbPath)
    {
        DbPath = dbPath;
    }

    @Override
    public IContactList getContactsFromDB()
    {
        try
        {
            Class.forName(DriverName);
            Connection con = DriverManager.getConnection(DbPath);

            /*Beispiel für PreparedStatement

            PreparedStatement statement;
            statement = con.prepareStatement("INSERT INTO Contacts Values(?, ?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, "name");
            statement.setString(2, "nachname");

            statement.executeUpdate();*/
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
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
          Class.forName(DriverName);
          con = DriverManager.getConnection(DbPath);

          Statement stmt = null;
          stmt = con.createStatement();


          ResultSet rst = stmt.executeQuery(query);


        }
        catch(ClassNotFoundException e)
        {
            IErrorLog.saveError("BLContacts","Treiber nicht gefunden",e.toString());
        }
        catch (SQLException e)
        {
            IErrorLog.saveError("BLContacts","Fehler in SQL",e.toString());
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
