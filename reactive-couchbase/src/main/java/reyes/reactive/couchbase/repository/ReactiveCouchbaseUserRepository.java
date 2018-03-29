package reyes.reactive.couchbase.repository;

import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import reactor.core.publisher.Mono;
import reyes.reactive.couchbase.model.User;

public interface ReactiveCouchbaseUserRepository extends ReactiveCouchbaseRepository<User, String> {

    Mono<User> findTopByActiveMinutesGreaterThanOrderByActiveMinutes(int activeMinutes);
}
