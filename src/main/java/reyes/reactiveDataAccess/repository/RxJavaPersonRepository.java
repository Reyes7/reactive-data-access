package reyes.reactiveDataAccess.repository;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import reyes.reactiveDataAccess.model.Person;

public interface RxJavaPersonRepository extends RxJava2CrudRepository<Person, String> {

    Flowable<Person> findByLastName(String lastName);

    Flowable<Person> findByLastName(Single<String> lastName);

    @Query("{'firstName' : ?0, 'lastName' : ?1}")
    Maybe<Person> findByFirstNameAndLastNameQuery(String firstName, String lastName);

    @Tailable
    Flowable<Person> findWithTailabeCursorBy();
}