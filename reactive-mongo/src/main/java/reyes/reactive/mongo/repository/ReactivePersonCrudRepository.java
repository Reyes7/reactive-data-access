package reyes.reactive.mongo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reyes.reactive.mongo.model.Person;

public interface ReactivePersonCrudRepository extends ReactiveCrudRepository<Person, String> {

    Flux<Person> findByLastName(String lastName);

}
