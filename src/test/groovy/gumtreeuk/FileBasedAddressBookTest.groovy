package gumtreeuk

import spock.lang.Specification

import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import static gumtreeuk.Gender.*
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class FileBasedAddressBookTest extends Specification {

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

    private static def contact(Contact contact) {
        return sameBeanAs(contact)
    }

    private static toLocalDate = toLocalDateFunc()

    private static def toLocalDateFunc() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
        return {String s -> LocalDate.parse(s, format)}
    }

    private def getPath(String str) {
        return Paths.get(getClass().getResource(str).toURI())
    }
}
