package gumtreeuk

import spock.lang.Specification

import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalQueries

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs

class AddressBookSpec extends Specification {

    protected static def contact(Contact contact) {
        return sameBeanAs(contact)
    }

    protected static toLocalDate = toLocalDateFunc()

    protected static def toLocalDateFunc() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/uu");
        return { String s ->
            LocalDate.parse(s, format).with({ Temporal temporal ->
                LocalDate now = LocalDate.now();

                if (now.getYear() < temporal.query(TemporalQueries.localDate()).getYear()) {
                    return temporal.minus(100, ChronoUnit.YEARS);
                }

                return temporal
            } as TemporalAdjuster);
        }
    }

    protected def getPath(String str) {
        return Paths.get(getClass().getResource(str).toURI())
    }
}
