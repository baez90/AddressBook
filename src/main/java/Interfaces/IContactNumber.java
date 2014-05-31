package Interfaces;

import Model.ContactNumberType;

/**
 * Created by baez on 29.05.14.
 */
public interface IContactNumber extends Comparable<IContactNumber> {
    public ContactNumberType getType();
}
