package BusinessLogic;

import Interfaces.*;
import Model.*;
import Model.Error;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.stream.Collectors;

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
        ConnectionPath = "jdbc:sqlite:" + DbPath;
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
                    ErrorLog.getInstance().add(new Error("BlContacts", "initDB", "Fehler beim erstellen des Datenbankfiles", "", null));
                    return false;
                }

            } catch (IOException e) {
                ErrorLog.getInstance().add(new Error("BlContacts", "initDB", "", e.toString(), e.getStackTrace()));
                return false;
            }
            Statement stmt;
            Connection connection = prepareConnection();
            try {
                if (connection == null) {
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
                        "ZipCode VARCHAR(20), " +
                        "City VARCHAR(100), " +
                        "BirthDate DATE );";

                String sqlTableContactList = "CREATE TABLE IF NOT EXISTS ContactsNumbers " +
                        "(ContactsNumbersID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                        " ContactID  INTEGER, " +
                        " Number VARCHAR(255), " +
                        " NumberType INTEGER)";

                stmt.executeUpdate(sqlTableContacts);
                stmt.executeUpdate(sqlTableContactList);
                connection.close();
                stmt.close();

            } catch (Exception e) {
                ErrorLog.getInstance().add(new Error("BlContacts", "InitDB", "", e.toString(), e.getStackTrace()));
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
        Connection connection = prepareConnection();
        if (connection == null) {
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
            IContactNumberList numberList = getContactNumbersByID(contact.getContactID());
            /*
            Alle Kontakte welche bereits vorhanden waren aktualisiert
            */
            if (!updateContactNumbers(contact.getContactNumbers().stream().filter(numberList::contains).collect(Collectors.toCollection(ContactNumberList::new)))) {
                return 0;
            }
            /*
            Alle neuen Kontakte werden hinzugefügt
            */
            if (!createContactNumbers(contact.getContactNumbers().stream().filter(n -> !numberList.contains(n)).collect(Collectors.toCollection(ContactNumberList::new)), contact.getContactID())) {
                return 0;
            }
            /*
            Alle Kontakte welche nicht mehr vorhanden sind gelöscht
            */
            if (!removeContactNumbers(numberList.stream().filter(n -> !contact.getContactNumbers().contains(n)).collect(Collectors.toCollection(ContactNumberList::new)))) {
                return 0;
            }
            return 1;
        } catch (SQLException e) {
            ErrorLog.getInstance().add(new Error("BlContacts", "updateContactInDB", e.getMessage(), e.toString(), e.getStackTrace()));
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
        Connection connection = prepareConnection();
        if (connection == null) {
            return -1;
        } else if (!ContactExistsInDatabase(contact)) {
            return 0;
        }
        try {
            PreparedStatement contactStmt = connection.prepareStatement("DELETE FROM Contacts WHERE ContactID = ?;");
            contactStmt.setInt(1, contact.getContactID());
            contactStmt.executeUpdate();
            contactStmt.close();
            connection.close();
            if (!removeContactNumbers(contact.getContactNumbers())) {
                return 2;
            }
            return 1;
        } catch (SQLException e) {
            ErrorLog.getInstance().add(new Error("BlContacts", "removeContactInDB", e.getMessage(), e.toString(), e.getStackTrace()));
        }
        return 2;
    }

    /**
     * Legt neuen Kontakt in der Datenbank an
     *
     * @param contact Kontakt welcher angelegt werden soll
     * @return Fehlercode/ID des neuen Kontakts
     * -1 Fehler aufgetreten
     * 0 Kontakt bereits aufgetreten, wird geupdated
     * größer 1 Kontakt erstellt
     */
    @Override
    public int createContactInDB(IContact contact) {

        try {
            if (!ContactExistsInDatabase(contact)) {
                Connection connection = prepareConnection();
                String houseNumber = contact.getAddress().getStreetAddress();
                String street = IUtil.extractStreet(contact.getAddress().getStreetAddress());
                houseNumber = houseNumber.substring(street.length(), houseNumber.length());

                if (connection == null) {
                    return -1;
                }
                PreparedStatement contactStmt = connection.prepareStatement("INSERT INTO Contacts (FirstName, LastName, MailAddress, Street, HouseNumber, ZipCode, City, BirthDate) VALUES(?,?,?,?,?,?,?,?);");
                contactStmt.setString(1, contact.getFirstName());
                contactStmt.setString(2, contact.getLastName());
                contactStmt.setString(3, contact.getMailAddress().toLowerCase());
                contactStmt.setString(4, street);
                contactStmt.setString(5, houseNumber);
                contactStmt.setString(6, contact.getAddress().getZipCode());
                contactStmt.setString(7, contact.getAddress().getCity());
                contactStmt.setDate(8, new Date(contact.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                contactStmt.executeUpdate();

                contactStmt.close();
                connection.close();
                if (!createContactNumbers(contact.getContactNumbers(), contact.getContactID())) {
                    return -1;
                }
            } else {
                updateContactInDB(contact);
                return 0;
            }
        } catch (Exception e) {
            ErrorLog.getInstance().add(new Error("BlContacts", "createContactInDB", "", e.toString(), e.getStackTrace()));
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
        ContactList list = new ContactList();
        Connection connection = prepareConnection();
        Statement stmt;
        ResultSet contactRs;
        try {
            if (connection == null) {
                return null;
            }
            stmt = connection.createStatement();
            contactRs = stmt.executeQuery("SELECT * FROM Contacts;");
            while (contactRs.next()) {
                IContact tempContact = new Contact(contactRs.getInt("ContactID"), contactRs.getString("FirstName"), contactRs.getString("LastName"), contactRs.getString("MailAddress"),
                        contactRs.getDate("BirthDate").toLocalDate());
                tempContact.setAddress(new Address(contactRs.getString("Street") + " " + contactRs.getString("HouseNumber"), contactRs.getString("ZipCode"), contactRs.getString("City")));
                tempContact.setContactNumbers(getContactNumbersByID(tempContact.getContactID()));
                list.add(tempContact);
            }
            contactRs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            ErrorLog.getInstance().add(new Error("BlContacts", "getContactsFromDB", "", e.toString(), e.getStackTrace()));
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
        Connection connection = prepareConnection();
        boolean contactExists = true;
        if (connection == null) {
            return true;
        }
        try {
            pStmt = connection.prepareStatement("SELECT * FROM Contacts WHERE LastName = ? AND FirstName = ?;");
            pStmt.setString(1, contact.getLastName());
            pStmt.setString(2, contact.getFirstName());
            resultSet = pStmt.executeQuery();
            contactExists = resultSet.next();
            pStmt.close();
            resultSet.close();
            connection.close();
            return contactExists;
        } catch (Exception e) {
            ErrorLog.getInstance().add(new Error("BlContacts", "ContactExistsInDatabase", "", e.toString(), e.getStackTrace()));
        }
        return contactExists;
    }

    /**
     * initialisiert das Connection-Objekt
     *
     * @return boolsches Ergebnis ob Connection erfolgreich angelegt werden konnte
     */
    private Connection prepareConnection() {
        Connection connection;
        try {
            /*
            Name des verwendeten Datenbank-Treibers
            */
            String driverName = "org.sqlite.JDBC";
            Class.forName(driverName);
            connection = DriverManager.getConnection(ConnectionPath);
            return connection;
        } catch (ClassNotFoundException e) {
            ErrorLog.getInstance().add(new Error("BlContacts", "prepareConnection", "Fehler beim Laden des DB-Treibers", e.toString(), e.getStackTrace()));
        } catch (SQLException e) {
            ErrorLog.getInstance().add(new Error("BlContacts", "prepareConnection", e.getMessage(), e.toString(), e.getStackTrace()));
        }
        return null;
    }

    /**
     * Liest ContactID eines neu erstellten Kontaktes aus der DB
     *
     * @param contact Kontakt welcher gerade erstellt wurde
     * @return ContactID des neu erstellten Kontakts
     */
    private int getContactID(IContact contact) {
        Connection connection = prepareConnection();
        ResultSet rs;
        int id = 0;
        if (connection == null) {
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
            ErrorLog.getInstance().add(new Error("BlContacts", "getContactID", e.getMessage(), e.toString(), e.getStackTrace()));
        }
        return id;
    }

    /**
     * Hilfsmethode für Update, liest alle Rufnummern eines Kontakts aus der Datenbank
     *
     * @param id ID für welche alle Rufnummern gesucht werden sollen
     * @return IContactNumberList für den Kontakt
     */
    private IContactNumberList getContactNumbersByID(int id) {
        Connection connection = prepareConnection();
        ResultSet numberRs;
        IContactNumberList tempList = new ContactNumberList();

        if (connection != null) {
            try {
                PreparedStatement numberStmt = connection.prepareStatement("SELECT * FROM ContactsNumbers WHERE ContactID = ?;");

                numberStmt.setInt(1, id);
                numberRs = numberStmt.executeQuery();

                while (numberRs.next()) {
                    switch (numberRs.getInt("NumberType")) {
                        case 0:
                            tempList.add(new ContactNumber(numberRs.getInt("ContactsNumbersID"), ContactNumberType.Mobile, numberRs.getString("Number")));
                            break;
                        case 1:
                            tempList.add(new ContactNumber(numberRs.getInt("ContactsNumbersID"), ContactNumberType.Home, numberRs.getString("Number")));
                            break;
                        case 2:
                            tempList.add(new ContactNumber(numberRs.getInt("ContactsNumbersID"), ContactNumberType.Work, numberRs.getString("Number")));
                            break;
                    }

                }
            } catch (SQLException e) {
                ErrorLog.getInstance().add(new Error("BlContacts", "getContactNumbersByID", e.getMessage(), e.toString(), e.getStackTrace()));
            }
        }
        return tempList;
    }

    /**
     * Hilfsmethode zum erstellen von ContactsNumbers
     *
     * @param contactNumbers IContactNumberList welche alle zu erstellenden Nummern enthält
     * @param contactID      ID des Kontakts für welchen die Nummern angelegt werden sollen
     */
    private boolean createContactNumbers(IContactNumberList contactNumbers, int contactID) {
        Connection connection = prepareConnection();
        if (connection != null) {
            try {
                PreparedStatement phoneStmt = connection.prepareStatement("INSERT INTO CONTACTSNUMBERS (ContactID, Number, NumberType) VALUES (?,?,?);");
                for (IContactNumber nr : contactNumbers) {
                    phoneStmt.setInt(1, contactID);
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
                connection.close();
                return true;
            } catch (SQLException e) {
                ErrorLog.getInstance().add(new Error("BlContacts", "createContactNumbers", "SQL-Exception", e.toString(), e.getStackTrace()));
                return false;
            }
        }
        return false;
    }

    /**
     * Hilfsmethode zum entfernen einer Liste von IContactsNumbers
     *
     * @param contactNumbers IContactNumberList welche alle zu löschenden Nummern enthält
     * @return boolsches Ergebnis ob alle Elemente erfolgreich gelöscht werden konnten#
     */
    private boolean removeContactNumbers(IContactNumberList contactNumbers) {
        Connection connection = prepareConnection();
        if (connection != null) {
            try {
                PreparedStatement pStmt = connection.prepareStatement("DELETE FROM CONTACTSNUMBERS WHERE ContactsNumbersID = ?;");
                contactNumbers.forEach(n -> {
                    try {
                        pStmt.setInt(1, n.getContactNumbersID());
                        pStmt.executeUpdate();
                    } catch (SQLException e) {
                        ErrorLog.getInstance().add(new Error("BlContacts", "removeContactNumbers", e.getMessage(), e.toString(), e.getStackTrace()));
                    }
                });
            } catch (SQLException e) {
                ErrorLog.getInstance().add(new Error("BlContacts", "removeContactNumbers", e.getMessage(), e.toString(), e.getStackTrace()));
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean updateContactNumbers(IContactNumberList contactNumbers) {
        Connection connection = prepareConnection();

        if (connection != null) {
            try {
                PreparedStatement updateStmt = connection.prepareStatement("UPDATE ContactsNumbers SET Number = ?, NumberType = ? WHERE ContactsNumbersID = ?;");
                contactNumbers.forEach(n -> {
                    try {
                        updateStmt.setString(1, n.getNumber());
                        updateStmt.setInt(3, n.getContactNumbersID());
                        switch (n.getType()) {
                            case Mobile:
                                updateStmt.setInt(2, 0);
                                break;
                            case Home:
                                updateStmt.setInt(2, 1);
                                break;
                            case Work:
                                updateStmt.setInt(2, 2);
                                break;
                        }
                        updateStmt.executeUpdate();
                    } catch (SQLException e) {
                        ErrorLog.getInstance().add(new Error("BlContacts", "updateContactNumbers", e.getMessage(), e.toString(), e.getStackTrace()));
                    }
                });
                return true;
            } catch (SQLException e) {
                ErrorLog.getInstance().add(new Error("BlContacts", "updateContactNumbers", e.getMessage(), e.toString(), e.getStackTrace()));
                return false;
            }
        }
        return false;
    }

    /**
     * Prüft ob die Datenbank alle Tabellen enthält
     *
     * @return boolsches Ergebnis ob alle benötigten Tabellen vorhanden sind
     */
    private boolean dbIsValid() {
        Connection connection = prepareConnection();
        if (connection == null) {
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
            ErrorLog.getInstance().add(new Error("BlContacts", "dbIsValid", e.getMessage(), e.toString(), e.getStackTrace()));
        }
        return (contactsTableExist & contactsNumbersTableExist);
    }
}