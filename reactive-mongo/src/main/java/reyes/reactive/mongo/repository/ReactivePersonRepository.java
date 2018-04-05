package reyes.reactive.mongo.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reyes.reactive.mongo.model.Person;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface ReactivePersonRepository extends ReactiveMongoRepository<Person, String> {

    CompletableFuture<Person> findFirstByFirstName(String firstName);

    @Query("{}")
    Stream<Person> all();
}
