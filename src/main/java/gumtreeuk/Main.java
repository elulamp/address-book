package gumtreeuk;


import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        String addressBookResource = "AddressBook";

        AddressBookOracle oracle = new AddressBookOracle(new FileBasedAddressBook(getPath(addressBookResource)));

        System.out.println(String.format("Hello this is Address book oracle for '%s' \n", addressBookResource));
        System.out.println(
                String.format("There are %d males in the address book", oracle.tellMeHowManyMenAreInTheAddressBook()));
        System.out.println(
                String.format("The oldest contact in address book is %s",
                        oracle.tellMeWhoIsTheOldestContactInTheAddressBook().get().name));
        System.out.println(
                String.format(
                        "Contact Bill McKnight is %d days older than contact Paul Robinson",
                        oracle.tellMeAgeDifferenceBetweenContactsInDays("Bill McKnight", "Paul Robinson")
                )
        );

    }

    private static Path getPath(String str) throws URISyntaxException {
        return Paths.get(Main.class.getClassLoader().getResource(str).toURI());
    }
}
