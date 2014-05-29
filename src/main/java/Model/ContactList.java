package Model;

import java.util.LinkedList;

/**
 * Created by baez on 29.05.14.
 */
public class ContactList extends LinkedList<IContact> implements IContactList {
    @Override
    public IContactList getContactsFromDB() {
        //TODO
        return null;
    }
}
