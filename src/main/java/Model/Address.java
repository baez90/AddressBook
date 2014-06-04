package Model;

import Interfaces.IAddress;

/**
 * Created by Baez on 03.06.2014.
 */
public class Address implements IAddress {
    /**
     * Straße und Hausnummer
     */
    private String StreetAddress;
    /**
     * Postleitzahl
     */
    private String ZipCode;
    /**
     * Wohnort
     */
    private String City;

    /**
     * Standard-Getter
     *
     * @return Straße und Hausnummer als String
     */
    @Override
    public String getStreetAddress() {
        return StreetAddress;
    }

    /**
     * Standard-Setter
     *
     * @param newStreetAddress neue Straße und Hausnummer als String
     */
    @Override
    public void setStreetAddress(String newStreetAddress) {
        StreetAddress = newStreetAddress;
    }

    /**
     * Standard-Getter
     *
     * @return Postleitzahl als String
     */
    @Override
    public String getZipCode() {
        return ZipCode;
    }

    /**
     * Standard-Setter
     *
     * @param newZipCode Postleitzahl als String
     */
    @Override
    public void setZipCode(String newZipCode) {
        ZipCode = newZipCode;
    }

    /**
     * Standard-Getter
     *
     * @return Ort als String
     */
    @Override
    public String getCity() {
        return City;
    }

    /**
     * Standard-Setter
     *
     * @param newCity neuer Ort als String
     */
    @Override
    public void setCity(String newCity) {
        City = newCity;
    }
}
