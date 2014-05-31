package Interfaces;

import java.time.LocalDate;

/**
 * @author baez
 */
public interface IContact extends Comparable<IContact> {
    public boolean dateOfBirthIsToday();

    public String getName();

    public String getFirstName();

    public String getMailAddress();

    public IContactNumberList getContactNumbers();

    public LocalDate getBirthDate();
}
