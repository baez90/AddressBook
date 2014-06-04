package Interfaces;

import Model.ContactNumberType;

/**
 * Created by baez on 29.05.14.
 */
public interface IContactNumber extends Comparable<IContactNumber> {
    public ContactNumberType getType();

    public void setType(ContactNumberType type);

    public String getNumber();

    public void setNumber(String number);
}
