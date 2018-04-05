package reyes.reactive.mongo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reyes.reactive.mongo.model.Person;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ReactiveEndpointsTest {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        StepVerifier.create(reactiveMongoTemplate.dropCollection(Person.class)).verifyComplete();
        Flux<Person> insertAll = reactiveMongoTemplate
                .insertAll(
                        Flux.just(
                                new Person("John", "Rambo", 30),
                                new Person("Johny", "Bravo", 27)
                        ).collectList());

        StepVerifier.create(insertAll).expectNextCount(2).verifyComplete();
    }

    @Test
    public void onPersonsEndpointICanGetTwoPersons(){
        FluxExchangeResult<Person> result = webTestClient.get()
                .uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Person.class);

        assert result.getStatus().value() == 200;

        List<Person> persons = result.getResponseBody()
                .collectList()
                .block();

        assert persons.size() == 2;
    }

    @Test
    public void onFirstNamePathVariableICanGetOnePerson(){
        FluxExchangeResult<Person> result = webTestClient.get()
                .uri("/persons/john")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Person.class);

        assert result.getStatus().value() == 200;

        List<Person> persons = result.getResponseBody()
                .collectList()
                .block();

        assert persons.size() == 1;
    }
}
