package Interfaces;

import javafx.beans.property.StringProperty;

/**
 * Interface für die Adresse
 *
 * @author Baez
 */
public interface IAddress {
    /**
     * Standard-Getter
     *
     * @return Straße und Hausnummer als String
     */
    public String getStreetAddress();

    /**
     * Standard-Setter
     *
     * @param newStreetAddress neue Straße und Hausnummer als String
     */
    public void setStreetAddress(String newStreetAddress);

    /**
     * Getter für TableView
     *
     * @return StreetAddress als StringProperty
     */
    public StringProperty getStreetAddressProperty();

    /**
     * Standard-Getter
     *
     * @return Postleitzahl als String
     */
    public String getZipCode();

    /**
     * Standard-Setter
     *
     * @param newZipCode Postleitzahl als String
     */
    public void setZipCode(String newZipCode);

    /**
     * Getter für TableView
     *
     * @return ZipCode als StringProperty
     */
    public StringProperty getZipCodeProperty();

    /**
     * Standard-Getter
     *
     * @return Ort als String
     */
    public String getCity();

    /**
     * Standard-Setter
     *
     * @param newCity neuer Ort als String
     */
    public void setCity(String newCity);

    /**
     * Getter für TableView
     *
     * @return City als StringProperty
     */
    public StringProperty getCityProperty();
}
