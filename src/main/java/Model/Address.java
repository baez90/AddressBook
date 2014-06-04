package Model;

import Interfaces.IAddress;

/**
 * Created by Baez on 03.06.2014.
 */
public class Address implements IAddress {
    /**
     * Standard-Getter
     *
     * @return Straße und Hausnummer als String
     */
    @Override
    public String getStreetAddress() {
        return null;
    }

    /**
     * Standard-Setter
     *
     * @param newStreetAddress neue Straße und Hausnummer als String
     */
    @Override
    public void setStreetAddress(String newStreetAddress) {

    }

    /**
     * Standard-Getter
     *
     * @return Postleitzahl als String
     */
    @Override
    public String getZipCode() {
        return null;
    }

    /**
     * Standard-Setter
     *
     * @param newZipCode Postleitzahl als String
     */
    @Override
    public void setZipCode(String newZipCode) {

    }

    /**
     * Standard-Getter
     *
     * @return Ort als String
     */
    @Override
    public String getCity() {
        return null;
    }

    /**
     * Standard-Setter
     *
     * @param newCity neuer Ort als String
     */
    @Override
    public void setCity(String newCity) {

    }
}
