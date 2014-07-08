package gumtreeuk;


import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class AddressBookOracle {

    private final AddressBook addressBook;

    public AddressBookOracle(AddressBook addressBook) {
        this.addressBook = addressBook;
    }


    public long tellMeHowManyMenAreInTheAddressBook() {

        Predicate<Contact> isMale = contact -> contact.gender == Gender.MALE;

        return addressBook.getContacts().stream().filter(isMale).count();
    }

    public Optional<Contact> tellMeWhoIsTheOldestContactInTheAddressBook() {
        Comparator<Contact> ageComparator =
                (c1, c2) -> c1.birthday.isBefore(c2.birthday) ? -1 : (c1.birthday.isEqual(c2.birthday) ? 0 : 1);

        return addressBook.getContacts().stream().sorted(ageComparator).findFirst();
    }

    public long tellMeAgeDifferenceBetweenContactsInDays(String firstContactName, String secondContactName) {
        Contact c1 = retrieveContact(firstContactName);
        Contact c2 = retrieveContact(secondContactName);

        return Math.abs(ChronoUnit.DAYS.between(c1.birthday, c2.birthday));
    }

    private Contact retrieveContact(String contactName) {
        Optional<Contact> contact = addressBook.findContactByName(contactName);

        if (!contact.isPresent()) {
            throw new IllegalArgumentException(String.format("Contact named '%s' does not exist in address book", contactName));
        }

        return contact.get();
    }
}
