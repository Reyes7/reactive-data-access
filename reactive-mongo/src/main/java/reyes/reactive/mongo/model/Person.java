package reyes.reactive.mongo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Person {

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    private int age;
}
