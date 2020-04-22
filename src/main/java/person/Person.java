package person;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Person {

    public static enum Gender {
        FEMALE, MALE;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private Gender gender;

    @Embedded
    private person.Address address;

    private String email;

    private String profession;

    public static Person randomPerson(){
        Faker faker = new Faker(new Locale("hu"));

        return Person.builder()
                .name(faker.name().fullName())
                .dob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .gender(faker.options().option(Gender.values()))
                .address(Address.builder()
                        .country(faker.address().country())
                        .state(faker.address().state())
                        .city(faker.address().city())
                        .streetAddress(faker.address().streetAddress())
                        .zip(faker.address().zipCode())
                        .build())
                .email(faker.internet().emailAddress())
                .profession(faker.company().profession()).build();
    }

}
