package Interfaces;

import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * @author baez
 */
public interface IContact extends Comparable<IContact> {
    public boolean dateOfBirthIsToday();

    public StringProperty getLastName();

    public StringProperty getFirstName();

    public StringProperty getMailAddress();

    public IContactNumberList getContactNumbers();

    public LocalDate getBirthDate();
}
