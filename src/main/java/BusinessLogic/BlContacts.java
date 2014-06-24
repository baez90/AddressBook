package BusinessLogic;

import Interfaces.*;
import Model.Address;
import Model.Contact;
import Model.ContactList;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author baez
 */
public class BlContacts implements IBlContacts {

    /**
     * Speichert den Pfad zur Datenbank-Datei ab
     */
    private String DbPath;
    /**
     * Pfad für Verbindung zur DB
     */
    private String ConnectionPath;
    /**
     * Connection-Objekt welches zur Laufzeit initialisiert wird
     */
    private Connection connection = null;

    /**
     * Standard-Getter für DbPath
     *
     * @return Pfad zur Datenbank-Datei
     */
    @Override
    public String getDbPath() {
        return DbPath;
    }

    /**
     * Setter für DbPath
     * Setzt auch Connection-String für die Verbindung zur DB
     *
     * @param dbPath Pfad zum Datenbank-File
     */
    @Override
    public void setDbPath(String dbPath) {
        DbPath = dbPath;
        ConnectionPath = "jdbc:sqlite://" + DbPath;
    }

    /**
     * Initialisiert die Datenbank, legt die Tabellen an
     *
     * @return boolsches Ergebnis ob Datenbank erfolgreich angelegt wurde
     */
    @Override
    public boolean initDB() {
        if (!dbIsValid()) {
            File databaseFile = new File(DbPath);
            try {
                if (databaseFile.createNewFile()) {
                    IErrorLog.saveError("BlContacts", "Fehler beim Erstellen des Datenbankfiles", "");
                    return false;
                }

            } catch (IOException e) {
                IErrorLog.saveError("BlContacts", "Fehler beim Erstellen des Datenbankfiles", e.toString());
                return false;
            }
            Statement stmt;
            try {
                if (!prepareConnection()) {
                    return false;
                }
                stmt = connection.createStatement();

                String sqlTableContacts = "CREATE TABLE IF NOT EXISTS Contacts " +
                        "(ContactID INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                        "FirstName VARCHAR(255), " +
                        "LastName VARCHAR(255), " +
                        "MailAddress VARCHAR(200), " +
                        "Street VARCHAR(255), " +
                        "HouseNumber VARCHAR(50), " +
                        "ZipCode INTEGER, " +
                        "City VARCHAR(100), " +
                        "BirthDate DATE );";

                String sqlTableContactList = "CREATE TABLE IF NOT EXISTS ContactsNumbers " +
                        "(ContactsNumbersID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                        " ContactID  INTEGER, " +
                        " Number VARCHAR(255), " +
                        " NumberType VARCHAR(255))";

                stmt.executeUpdate(sqlTableContacts);
                stmt.executeUpdate(sqlTableContactList);
                connection.close();
                stmt.close();

            } catch (Exception e) {
                IErrorLog.saveError("BlContacts", "Fehler bei INITDB", e.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * Kontakt in der DB updaten
     *
     * @param contact Kontakt welcher geupdatet werden soll
     * @return Erfolgs-Code
     * -1 bei Verbindungsfehler zur DB
     * 0 bei SQL-Fehler
     * 1 bei Erfolg
     */
    @Override
    public int updateContactInDB(IContact contact) {

        if (!prepareConnection()) {
            return -1;
        }
        String houseNumber = contact.getAddress().getStreetAddress();
        String street = IUtil.extractStreet(contact.getAddress().getStreetAddress());
        houseNumber = houseNumber.substring(street.length(), houseNumber.length()).trim();

        try {
            PreparedStatement updateStmt = connection.prepareStatement("UPDATE Contacts SET FirstName = ?, LastName = ?, MailAddress = ?, Street = ?, HouseNumber = ?, ZipCode = ?, City = ?, BirthDate = ? WHERE ContactID = ?;");
            updateStmt.setString(1, contact.getFirstName());
            updateStmt.setString(2, contact.getLastName());
            updateStmt.setString(3, contact.getMailAddress());
            updateStmt.setString(4, street);
            updateStmt.setString(5, houseNumber);
            updateStmt.setString(6, contact.getAddress().getZipCode());
            updateStmt.setString(7, contact.getAddress().getCity());
            updateStmt.setDate(8, new Date(contact.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            updateStmt.setInt(9, contact.getContactID());

            updateStmt.executeUpdate();

            updateStmt.close();
            connection.close();
            return 1;
        } catch (SQLException e) {
            IErrorLog.saveError("BlContacts", "Fehler beim Update eines Kontakts", e.toString());
        }
        return 0;
    }

    /**
     * Entfernt Kontakt aus der Datenbank
     *
     * @param contact Kontakt welcher gelöscht werden soll
     * @return Fehlercode
     * -1 bei Verbindungsfehler
     * 0 falls Kontakt nicht existiert
     * 1 falls erfolgrich gelöscht
     * 2 falls Fehler aufgetreten ist
     */
    @Override
    public int removeContactInDB(IContact contact) {
        ResultSet rs;
        if (!prepareConnection()) {
            return -1;
        } else if (!ContactExistsInDatabase(contact)) {
            return 0;
        }
        try {
            PreparedStatement contactStmt = connection.prepareStatement("DELETE FROM Contacts WHERE ContactID = ?;");
            PreparedStatement queryStmt = connection.prepareStatement("SELECT * FROM ContactsNumbers WHERE ContactID = ?;");
            PreparedStatement delStmt = connection.prepareStatement("DELETE FROM ContactsNumbers WHERE contactsnumbersid = ?;");

            contactStmt.setInt(1, contact.getContactID());
            queryStmt.setInt(1, contact.getContactID());
            contactStmt.executeUpdate();
            rs = queryStmt.executeQuery();
            while (rs.next()) {
                delStmt.setInt(1, rs.getInt("ContactNumbersID"));
                delStmt.executeUpdate();
            }
            rs.close();
            contactStmt.close();
            queryStmt.close();
            delStmt.close();
            connection.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 2;
    }

    /**
     * Legt neuen Kontakt in der Datenbank an
     *
     * @param contact Kontakt welcher angelegt werden soll
     * @return Fehlercode
     * -1 Fehler aufgetreten
     * 0 Kontakt bereits aufgetreten, wird geupdated
     * 1 Kontakt erstellt
     */
    @Override
    public int createContactInDB(IContact contact) {
        try {
            if (!ContactExistsInDatabase(contact)) {
                String houseNumber = contact.getAddress().getStreetAddress();
                String street = IUtil.extractStreet(contact.getAddress().getStreetAddress());
                houseNumber = houseNumber.substring(street.length(), houseNumber.length());

                if (!prepareConnection()) {
                    return -1;
                }
                PreparedStatement contactStmt = connection.prepareStatement("INSERT INTO Contacts (FirstName, LastName, MailAddress, Street, HouseNumber, ZipCode, City, BirthDate) VALUES(?,?,?,?,?,?,?,?);");
                PreparedStatement phoneStmt = connection.prepareStatement("INSERT INTO CONTACTSNUMBERS (ContactID, Number, NumberType) VALUES (?,?,?);");
                contactStmt.setString(1, contact.getFirstName());
                contactStmt.setString(2, contact.getLastName());
                contactStmt.setString(3, contact.getMailAddress().toLowerCase());
                contactStmt.setString(4, street);
                contactStmt.setString(5, houseNumber);
                contactStmt.setString(6, contact.getAddress().getZipCode());
                contactStmt.setString(7, contact.getAddress().getCity());
                contactStmt.setDate(8, new Date(contact.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                contactStmt.executeUpdate();

                int contactId = getContactID(contact);

                for (IContactNumber nr : contact.getContactNumbers()) {
                    phoneStmt.setInt(1, contactId);
                    phoneStmt.setString(2, nr.getNumber());
                    switch (nr.getType()) {
                        case Mobile:
                            phoneStmt.setInt(3, 0);
                            break;
                        case Home:
                            phoneStmt.setInt(3, 1);
                            break;
                        case Work:
                            phoneStmt.setInt(3, 2);
                            break;
                        default:
                            phoneStmt.setInt(3, 5);
                    }
                    phoneStmt.executeUpdate();
                }
                phoneStmt.close();
                contactStmt.close();
                connection.close();
            } else {
                updateContactInDB(contact);
                return 0;
            }
        } catch (Exception e) {
            IErrorLog.saveError("BLContacts", "Fehler bei Create Contact", e.toString());
            return -1;
        }
        return getContactID(contact);
    }

    /**
     * liest alle Kontakte aus der Datenbank
     *
     * @return Liste von allen Kontakten
     */
    @Override
    public ContactList getContactsFromDB() {
        //TODO Rufnummern berücksichtigen
        ContactList list = new ContactList();
        Statement stmt;
        ResultSet rs;
        try {
            if (!prepareConnection()) {
                return null;
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Contacts;");
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
            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            IErrorLog.saveError("BLContacts", "Fehler beim laden aller Kontakte", e.toString());
        }
        return list;
    }

    /**
     * Prüft anhand von Vor und Nachnamen ob Kontakt bereits existiert
     *
     * @param contact Kontakt welcher überprüft werden soll
     * @return boolsches Ergebnis ob Kontakt bereits existiert
     */
    private boolean ContactExistsInDatabase(IContact contact) {
        PreparedStatement pStmt;
        ResultSet resultSet;
        boolean contactExists;
        try {
            prepareConnection();
            pStmt = connection.prepareStatement("SELECT * FROM Contacts WHERE LastName LIKE ? AND FirstName LIKE ?;");
            pStmt.setString(1, "%" + contact.getLastName() + "%");
            pStmt.setString(2, "%" + contact.getFirstName() + "%");
            resultSet = pStmt.executeQuery();
            contactExists = resultSet.next();
            pStmt.close();
            resultSet.close();
            connection.close();
            return contactExists;
        } catch (Exception e) {
            IErrorLog.saveError("BLContacts", "Fehler bei ContactExistsInDatabase", e.toString());
        }
        return true;
    }

    /**
     * initialisiert das Connection-Objekt
     *
     * @return boolsches Ergebnis ob Connection erfolgreich angelegt werden konnte
     */
    private boolean prepareConnection() {
        try {
            /*
            Name des verwendeten Datenbank-Treibers
            */
            String driverName = "org.sqlite.JDBC";
            Class.forName(driverName);
            connection = DriverManager.getConnection(ConnectionPath);
            return true;
        } catch (ClassNotFoundException e) {
            IErrorLog.saveError("BlContacts", "Fehler beim Laden des Treibers", e.toString());
        } catch (SQLException e) {
            IErrorLog.saveError("BlContacts", "Fehler beim Herstellen der Verbindung", e.toString());
        }
        return false;
    }

    /**
     * Liest ContactID eines neu erstellten Kontaktes aus der DB
     *
     * @param contact Kontakt welcher gerade erstellt wurde
     * @return ContactID des neu erstellten Kontakts
     */
    private int getContactID(IContact contact) {
        ResultSet rs;
        int id = 0;
        if (!prepareConnection()) {
            return -1;
        }
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT ContactID FROM Contacts WHERE FirstName = ? AND LastName = ?;");
            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());

            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("ContactID");
            } else
                id = -2;

            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Prüft ob die Datenbank alle Tabellen enthält
     *
     * @return boolsches Ergebnis ob alle benötigten Tabellen vorhanden sind
     */
    private boolean dbIsValid() {
        if (!prepareConnection()) {
            return false;
        }
        boolean contactsTableExist = false;
        boolean contactsNumbersTableExist = false;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet metaResult = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (metaResult.next()) {
                if (metaResult.getString("TABLE_NAME").equals("Contacts")) {
                    contactsTableExist = true;
                } else if (metaResult.getString("TABLE_NAME").equals("ContactsNumbers")) {
                    contactsNumbersTableExist = true;
                }
            }
            connection.close();
            metaResult.close();
        } catch (SQLException e) {
            IErrorLog.saveError("BlContacts", "Fehler bei dbValid", e.toString());
        }
        return (contactsTableExist & contactsNumbersTableExist);
    }
}