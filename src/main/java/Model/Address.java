package Model;

import Interfaces.IAddress;
import javafx.beans.property.StringProperty;

/**
 * Created by Baez on 03.06.2014.
 */
public class Address implements IAddress {
    @Override
    public StringProperty getStreetAddress() {
        return null;
    }

    @Override
    public void setStreetAddress(StringProperty newStreetAddress) {

    }

    @Override
    public StringProperty getZipCode() {
        return null;
    }

    @Override
    public void setZipCode(StringProperty newZipCode) {

    }

    @Override
    public StringProperty getCity() {
        return null;
    }

    @Override
    public void setCity(StringProperty newCity) {

    }
}
