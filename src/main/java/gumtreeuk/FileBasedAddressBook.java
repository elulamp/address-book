package gumtreeuk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileBasedAddressBook implements AddressBook {

    private static final String VALUE_DELIMITER = ", ";
    private static final int NAME = 0;
    private static final int GENDER = 1;
    private static final int BIRTHDAY = 2;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");

    private final Path file;

    public FileBasedAddressBook(Path file) {
        this.file = file;
    }

    @Override
    public List<Contact> getContacts() {
        return readContactsFromFile();
    }

    @Override
    public Optional<Contact> findContactByName(final String name) {
        return getContacts().stream().filter(contact -> name.equalsIgnoreCase(contact.name)).findFirst();
    }

    private List<Contact> readContactsFromFile() {

        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Was not able to read from '%s'", file));
        }

        List<Contact> result = new ArrayList<>(lines.size());
        for (String line : lines) {
            String[] values = line.split(VALUE_DELIMITER);

            Contact.Builder builder = new Contact.Builder();
            builder.withName(values[NAME]);
            builder.withGender(toGender(values[GENDER]));
            builder.withBirthday(toLocalDate(values[BIRTHDAY]));

            result.add(builder.build());
        }

        return result;
    }

    private static Gender toGender(String value) {
        return Gender.valueOf(value.toUpperCase());
    }

    private static LocalDate toLocalDate(CharSequence value) {
        return LocalDate.parse(value, DATE_TIME_FORMATTER).with(temporal -> {
            // this is needed because Java 8 interprets two digit years as years between 2000-2099
            LocalDate now = LocalDate.now();

            if (now.getYear() < temporal.query(TemporalQueries.localDate()).getYear()) {
                return temporal.minus(100, ChronoUnit.YEARS);
            }

            return temporal;
        });
    }
}
