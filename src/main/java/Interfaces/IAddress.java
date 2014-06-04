package Interfaces;

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
}
