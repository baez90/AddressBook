package Interfaces;

import javafx.beans.property.StringProperty;

/**
 * @author Baez
 */
public interface IAddress {
    public StringProperty getStreetAddress();

    public void setStreetAddress(StringProperty newStreetAddress);

    public StringProperty getZipCode();

    public void setZipCode(StringProperty newZipCode);

    public StringProperty getCity();

    public void setCity(StringProperty newCity);
}
