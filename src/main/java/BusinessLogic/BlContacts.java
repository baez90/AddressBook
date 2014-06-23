package BusinessLogic;

import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IErrorLog;
import Model.Address;
import Model.Contact;
import Model.ContactList;

import java.io.File;
import java.io.IOException;
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
    private boolean unitTest = true;

    //private String DriverName = "com.mysql.jdbc.Driver";
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
    public int updateContactInDB(IContact contact) {
        String city = contact.getAddress().getCity();
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String mailAdress = contact.getMailAddress();
        LocalDate date = contact.getBirthDate();
        String street = "";
        String zipCode = contact.getAddress().getZipCode();
        //String houseNumber = contact.getAddress().getStreetAddress().replaceAll("[a-zA-Z]*","");
        String houseNumber = contact.getAddress().getStreetAddress();

        for (int i = 0; i <= houseNumber.length(); i++) {
            if (Character.isLetter(houseNumber.charAt(i))) {
                street += houseNumber.charAt(i);
            } else
                break;


        }
        houseNumber = houseNumber.substring(street.length(), houseNumber.length());

      /*  String query = "Update Contacts Set FirstName = '" + firstName + "',LastName = '" +
                       lastName + "',MailAdress = '" + mailAdress + "',Street = '" +
                       street + "',HouseNumber = '" + houseNumber + "',ZipCode = " +
                       zipCode + "',City = '" + city + "',Birthdate = '" + date + "'" +
                       "where ContactID = " + contact.getContactID();*/ // maybe useful ;)

             /*  String query = String.format("Update Contacts Set FirstName = '%s',LastName = '%s',MailAddress = '%s'," +
                "Street = '%s',HouseNumber = '%s',ZipCode = '%s',City = '%s'" +
                "where ContactID = %d"
                , firstName, lastName, mailAdress, street, houseNumber, zipCode, city,(int)contact.getContactID());
                */
        String query = String.format("Update Contacts Set FirstName = '%s',LastName = '%s',MailAddress = '%s'," +
                "Street = '%s',HouseNumber = '%s',ZipCode = '%s',City = '%s'" +
                "where LastName = '%s'"
                , firstName, lastName, mailAdress, street, houseNumber, zipCode, city, lastName);


        ExecuteQuery(query);

        return 0;
    }

    @Override
    public int removeContactInDB(IContact contact) {

        int contactID = contact.getContactID();
        String query = String.format("Delete From Contacts where ContactID = %d", contactID);

        ExecuteQuery(query);
        return 0;
    }

    @Override
    public int createContactInDB(IContact contact)  // Return 0 if new Contact else 1
    {

        boolean contactExistsInDatabase = ContactExistsInDatabase(contact);

        try {
            if (unitTest == true) {
                contactExistsInDatabase = false;
            }

            if (contactExistsInDatabase == false) {

                String city = contact.getAddress().getCity();
                String firstName = contact.getFirstName();
                String lastName = contact.getLastName();
                String mailAdress = contact.getMailAddress();
                LocalDate date = contact.getBirthDate();
                String street = "";
                String zipCode = contact.getAddress().getZipCode();
                //String houseNumber = contact.getAddress().getStreetAddress().replaceAll("[a-zA-Z]*","");
                String houseNumber = contact.getAddress().getStreetAddress();

                for (int i = 0; i <= houseNumber.length(); i++) {
                    if (Character.isLetter(houseNumber.charAt(i))) {
                        street += houseNumber.charAt(i);
                    } else
                        break;


                }
                houseNumber = houseNumber.substring(street.length(), houseNumber.length());


        /*String query = String.format("INSERT INTO Contacts Values('"+ firstName + "','"
                      + lastName + "','" + mailAdress + "','" + street +
                       "','" + houseNumber + "','" + zipCode + "','" + city + "','"
                       + date + "')");*/ // maybe useful

                String query = String.format("INSERT INTO Contacts Values('%d','%s','%s','%s','%s','%s','%s','%s','2012-2-21')"
                        , (int) (contact.getContactID() + Math.random() * 1000 + 1), firstName, lastName, mailAdress, street, houseNumber, zipCode, city);
                //TODO: Change here ContactID should be auto incremented but it isnt

                ExecuteQuery(query);


            } else {
                updateContactInDB(contact);
                return 1;
            }


        } catch (Exception e) {
            IErrorLog.saveError("BLContacts", "Fehler bei Create Contact", e.toString());
        }

        return 0;
    }

    @Override
    public boolean ContactExistsInDatabase(IContact contact) {
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;
        Connection connect = null;
        boolean temp;
        try {
            Class.forName(DriverName);
            connect = DriverManager.getConnection(getDbPath());
            pStmt = connect.prepareStatement("SELECT * FROM Contacts WHERE LastName LIKE ? AND FirstName LIKE ?;");
            pStmt.setString(1, "%" + contact.getLastName() + "%");
            pStmt.setString(2, "%" + contact.getFirstName() + "%");
            resultSet = pStmt.executeQuery();
            temp = resultSet.next();
            connect.close();
            pStmt.close();
            resultSet.close();
            return temp;
        } catch (Exception e) {
            IErrorLog.saveError("BLContacts", "Fehler bei ContactExistsInDatabase", e.toString());
        }
        return true;
    }


    @Override
    public boolean initDB() {
        String userdirectory = System.getProperty("user.dir");
        boolean checkIFFileExists = new File(userdirectory, "AddressBook.db").exists();
        String databaseString = "jdbc:sqlite://" + userdirectory + "//AddressBook.db";

        if (!checkIFFileExists) {
            File databaseFile = new File(userdirectory + "AddressBook.db");
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                IErrorLog.saveError("BlContacts", "Fehler beim Speichern des Datenbankfiles", e.toString());
                return false;

            }


            setDbPath(databaseString);

            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName(DriverName);
                System.out.println("Connecting to a selected database...");
                conn = DriverManager.getConnection(databaseString);
                System.out.println("Connected database successfully...");


                System.out.println("Creating table in given database...");
                stmt = conn.createStatement();

                String sqlTableContacts = "CREATE TABLE Contacts " +
                        "(ContactID INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
                        " FirstName VARCHAR(255), " +
                        " LastName VARCHAR(255), " +
                        " MailAddress VARCHAR(200), " +
                        " Street VARCHAR(255), " +
                        " HouseNumber VARCHAR(50), " +
                        " ZipCode INTEGER, " +
                        " City VARCHAR(100), " +
                        " BirthDate DATE )";
                //" PRIMARY KEY ( ContactID ))";

                String sqlTableContactList = "CREATE TABLE ContactsNumbers " +
                        "(ContactsNumbersID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        " ContactID  INTEGER, " +
                        " Number VARCHAR(255), " +
                        " NumberType VARCHAR(255) )";
                // " PRIMARY KEY ( ContactsNumbersID ))";

                stmt.executeUpdate(sqlTableContacts);
                System.out.println("Table Contacts erfolgreich erstellt");


                stmt.executeUpdate(sqlTableContactList);
                System.out.println("Table ContactsNumbers erfolgreich erstellt");

            } catch (Exception e) {
                System.out.println(e.toString());
                IErrorLog.saveError("BlContacts", "Fehler bei INITDB", e.toString());
                return false;
            } finally {
                try {
                    conn.close();
                    stmt.close();
                } catch (Exception e) {
                    IErrorLog.saveError("BLContacts", "Fehler bei schließen der Verbindung: INIT DB", e.toString());
                    return false;
                }
            }
            return true;

        } else {
            setDbPath(databaseString);
            return true;
        }
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
    public ContactList getContactsFromDB() // all contacts from Database Table Contacts
    {
        ContactList list = new ContactList();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(DriverName);
            con = DriverManager.getConnection(getDbPath());

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Contacts");
            while (rs.next()) {

                Contact tempContact = new Contact(rs.getInt("ContactID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("MailAddress"),
                        LocalDate.now());
                Address tempAdress = new Address();
                tempAdress.setCity(rs.getString("City"));
                tempAdress.setStreetAddress(rs.getString("Street") + rs.getString("HouseNumber"));
                tempAdress.setZipCode(rs.getString("ZipCode"));
                tempContact.setAddress(tempAdress);
                tempContact.setBirthDate(rs.getDate("BirthDate").toLocalDate());

                list.add(tempContact);

            }

        } catch (Exception e) {
            IErrorLog.saveError("BLContacts", "Fehler beim laden aller Kontakte", e.toString());
        } finally {
            try {
                con.close();
                stmt.close();
                rs.close();

            } catch (Exception e) {
                IErrorLog.saveError("BlContacts", "Fehler beim Schließen der Verbindung GetContacts", e.toString());
            }
        }


        return list;
    }


    @Override
    public void ExecuteQuery(String query) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rst = null;
        try {
            Class.forName(DriverName);
            con = DriverManager.getConnection(DbPath);


            stmt = con.createStatement();


            stmt.executeQuery(query);


        } catch (ClassNotFoundException e) {
            IErrorLog.saveError("BLContacts", "Treiber nicht gefunden", e.toString());
        } catch (SQLException e) {
            IErrorLog.saveError("BLContacts", "Fehler in SQL", e.toString());
        } catch (Exception e) {
            IErrorLog.saveError("BLContacts", "Unbekannter Fehler aufgetreten", e.toString());
        } finally {
            if (con != null)
                try {
                    con.close();
                    stmt.close();
                    rst.close();

                } catch (SQLException e) {
                    IErrorLog.saveError("BLContacts", "Fehler beim Schließen der Verbindung", e.toString());
                }
        }

    }
}