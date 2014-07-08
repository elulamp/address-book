package gumtreeuk

import org.hamcrest.Matchers

import static gumtreeuk.Gender.*
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class FileBasedAddressBookTest extends AddressBookSpec {

    def "should read all the contacts info from 'AddressBook' file"() {
        given:
            def addressBook = new FileBasedAddressBook(getPath("AddressBook"));

        when:
            def contacts = addressBook.getContacts();

        then:
            expect contacts, containsInAnyOrder(contact(bill), contact(paul), contact(gemma), contact(sarah), contact(wes))

        where:
            bill = new Contact("Bill McKnight", MALE, toLocalDate("16/03/77"))
            paul = new Contact("Paul Robinson", MALE, toLocalDate("15/01/85"))
            gemma = new Contact("Gemma Lane", FEMALE, toLocalDate("20/11/91"))
            sarah = new Contact("Sarah Stone", FEMALE, toLocalDate("20/09/80"))
            wes = new Contact("Wes Jackson", MALE, toLocalDate("14/08/74"))
    }

    def "should find contact by name from address book backed by 'AddressBook' file"() {
        given:
            def addressBook = new FileBasedAddressBook(getPath("AddressBook"));

        when:
            def retrievedContact = addressBook.findContactByName("Bill McKnight")

        then:
            expect retrievedContact.get(), Matchers.is(contact(bill))

        where:
            bill = new Contact("Bill McKnight", MALE, toLocalDate("16/03/77"))
    }

    def "should return empty list when reading contacts info from 'EmptyAddressBook'"() {
        given:
            def addressBook = new FileBasedAddressBook(getPath("EmptyAddressBook"));

        when:
            def contacts = addressBook.getContacts();

        then:
            contacts.isEmpty()
    }

    def "should not find a contact by name from address book backed by 'EmptyAddressBook' file"() {
        given:
            def addressBook = new FileBasedAddressBook(getPath("EmptyAddressBook"));

        when:
            def retrievedContact = addressBook.findContactByName("Some Contact")

        then:
            !retrievedContact.isPresent()

    }
}
