package gumtreeuk;

import java.time.LocalDate;

public class Contact {

    public final String name;
    public final Gender gender;
    public final LocalDate birthday;

    public Contact(String name, Gender gender, LocalDate birthday) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return String.format("'%s, %s, %s'", name, gender, birthday.format(Constants.DATE_TIME_FORMATTER));
    }

    public static class Builder {
        private String name = "John Doe";
        private Gender gender = Gender.MALE;
        private LocalDate birthday = LocalDate.now();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Contact build() {
            return new Contact(name, gender, birthday);
        }
    }
}
