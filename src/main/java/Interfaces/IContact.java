package Interfaces;

import java.time.LocalDate;

/**
 * @author baez
 */
public interface IContact extends Comparable<IContact> {
    public boolean dateOfBirthIsToday();

    public int getContactID();
    public String getLastName();

    public void setLastName(String lastName);
    public String getFirstName();

    public void setFirstName(String firstName);

    public String getMailAddress();

    public void setMailAddress(String mailAddress);
    public IContactNumberList getContactNumbers();

    public void setContactNumbers(IContactNumberList contactNumbers);
    public LocalDate getBirthDate();

    public void setBirthDate(LocalDate birthDate);

    public IAddress getAddress();

    public void setAddress(IAddress address);
}
