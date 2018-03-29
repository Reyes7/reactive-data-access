package reyes.reactive.couchbase.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reyes.reactive.couchbase.model.User;
import reyes.reactive.couchbase.repository.ReactiveCouchbaseUserRepository;

import java.time.Duration;

@RestController
public class ActivityTrackerController {

    @Autowired
    private ReactiveCouchbaseUserRepository userRepository;

    @GetMapping("/leader")
    Mono<User> getLeader() {
        return userRepository.findTopByActiveMinutesGreaterThanOrderByActiveMinutes(0);
    }

    @GetMapping(value = "/leaderStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<User> getLeaderStream() {
        return Flux.interval(Duration.ofSeconds(1L))
                .flatMap(x -> userRepository.findTopByActiveMinutesGreaterThanOrderByActiveMinutes(0));
    }
}
