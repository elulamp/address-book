package gumtreeuk;


import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

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

    private static Path getPath(String resStr) throws URISyntaxException, IOException {

        if (isInsideJar(resStr)) {
            return pathFromJar(resStr);
        }

        return pathFromDisk(resStr);
    }

    private static Path pathFromJar(String resStr) throws IOException {
        InputStream is = null;
        try {
            is = Main.class.getClassLoader().getResourceAsStream(resStr);
            Path tempFile = Files.createTempFile("address-book", null);
            tempFile.toFile().deleteOnExit();
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static Path pathFromDisk(String resStr) throws URISyntaxException {
        return Paths.get(Main.class.getClassLoader().getResource(resStr).toURI());
    }

    private static boolean isInsideJar(String resStr) {
        URL res = Main.class.getClassLoader().getResource(resStr);

        return res != null && res.toString().startsWith("jar:");
    }
}
