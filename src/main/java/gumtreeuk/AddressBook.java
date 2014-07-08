package gumtreeuk;

import java.util.List;
import java.util.Optional;

public interface AddressBook {

    List<Contact> getContacts();

    Optional<Contact> findContactByName(String name);

}
