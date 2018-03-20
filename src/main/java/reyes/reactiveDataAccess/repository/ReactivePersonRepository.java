package reyes.reactiveDataAccess.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reyes.reactiveDataAccess.model.Person;

public interface ReactivePersonRepository extends ReactiveCrudRepository<Person, String> {

    Flux<Person> findByLastName(String lastName);

}
