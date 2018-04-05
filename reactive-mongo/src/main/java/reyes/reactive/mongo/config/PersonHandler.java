package reyes.reactive.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reyes.reactive.mongo.model.Person;
import reyes.reactive.mongo.repository.ReactivePersonRepository;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class PersonHandler {

    @Autowired
    private ReactivePersonRepository reactivePersonRepository;

    public Mono<ServerResponse> all(ServerRequest serverRequest){
        return ok()
            .body(reactivePersonRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> byFirstName(ServerRequest serverRequest) {
        Optional<String> optPathVariable = Optional.ofNullable(serverRequest.pathVariable("firstName"));

        return optPathVariable.map(firstName -> reactivePersonRepository.findFirstByFirstName(firstName))
                .map(Mono::fromFuture)
                .map(personMono -> ok()
                    .body(BodyInserters.fromPublisher(personMono, Person.class))
                ).orElseThrow(() -> new IllegalStateException("Ooops !!!"));
    }
}