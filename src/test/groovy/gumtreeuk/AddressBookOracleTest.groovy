package gumtreeuk

import org.hamcrest.Matchers

import static gumtreeuk.Gender.MALE
import static spock.util.matcher.HamcrestSupport.that

class AddressBookOracleTest extends AddressBookSpec {

    private AddressBookOracle oracle;

    void setup() {
        oracle = new AddressBookOracle(new FileBasedAddressBook(getPath("AddressBook")))
    }

    def "should tell you how many men are in the address book"() {
        expect:
           oracle.tellMeHowManyMenAreInTheAddressBook() == 3
    }

    def "should tell you who is the oldest contact in the address book"() {
        expect:
            that oracle.tellMeWhoIsTheOldestContactInTheAddressBook().get(), Matchers.is(contact(wes))

        where:
            wes = new Contact("Wes Jackson", MALE, toLocalDate("14/08/74"))
    }

    def "should not return a contact when asking who is the oldest contact when address book is empty"() {
        given:
            def emptyAddressBookOracle = new AddressBookOracle(new FileBasedAddressBook(getPath("EmptyAddressBook")))

        when:
            def somebody = emptyAddressBookOracle.tellMeWhoIsTheOldestContactInTheAddressBook();

        then:
            !somebody.isPresent()

    }

    def "should tell you age difference between two contacts in days"() {
        expect:
            that oracle.tellMeAgeDifferenceBetweenContactsInDays(bill, paul), Matchers.is(2862L)

        where:
            bill = "Bill McKnight"
            paul = "Paul Robinson"
    }

    def "should fail if you ask age difference for contacts that do not exist in address book"() {
        when:
            oracle.tellMeAgeDifferenceBetweenContactsInDays(bill, unknownContact)

        then:
            thrown(IllegalArgumentException)

        where:
            bill = "Bill McKnight"
            unknownContact = "John Doe"
    }
}
