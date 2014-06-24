package Model;

import Interfaces.IAddress;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Baez on 03.06.2014.
 */
public class Address implements IAddress {
    /**
     * Straße und Hausnummer
     */
    private StringProperty StreetAddress;
    /**
     * Postleitzahl
     */
    private StringProperty ZipCode;
    /**
     * Wohnort
     */
    private StringProperty City;

    public Address() {
        StreetAddress = new SimpleStringProperty("");
        ZipCode = new SimpleStringProperty("");
        City = new SimpleStringProperty("");
    }

    public Address(String streetAddress, String zipCode, String city) {
        StreetAddress = new SimpleStringProperty(streetAddress);
        ZipCode = new SimpleStringProperty(zipCode);
        City = new SimpleStringProperty(city);
    }

    /**
     * Standard-Getter
     *
     * @return Straße und Hausnummer als String
     */
    @Override
    public String getStreetAddress() {
        return StreetAddress.getValue();
    }

    /**
     * Standard-Setter
     *
     * @param newStreetAddress neue Straße und Hausnummer als String
     */
    @Override
    public void setStreetAddress(String newStreetAddress) {
        StreetAddress.set(newStreetAddress);
    }

    /**
     * Getter für TableView
     *
     * @return StreetAddress als StringProperty
     */
    @Override
    public StringProperty getStreetAddressProperty() {
        return StreetAddress;
    }

    /**
     * Standard-Getter
     *
     * @return Postleitzahl als String
     */
    @Override
    public String getZipCode() {
        return ZipCode.getValue();
    }

    /**
     * Standard-Setter
     *
     * @param newZipCode Postleitzahl als String
     */
    @Override
    public void setZipCode(String newZipCode) {
        ZipCode.set(newZipCode);
    }

    /**
     * Getter für TableView
     *
     * @return ZipCode als StringProperty
     */
    @Override
    public StringProperty getZipCodeProperty() {
        return ZipCode;
    }

    /**
     * Standard-Getter
     *
     * @return Ort als String
     */
    @Override
    public String getCity() {
        return City.getValue();
    }

    /**
     * Standard-Setter
     *
     * @param newCity neuer Ort als String
     */
    @Override
    public void setCity(String newCity) {
        City.set(newCity);
    }

    /**
     * Getter für TableView
     *
     * @return City als StringProperty
     */
    @Override
    public StringProperty getCityProperty() {
        return City;
    }
}
