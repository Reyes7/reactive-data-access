package reyes.reactive.mongo;

import io.reactivex.Flowable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reyes.reactive.mongo.model.Person;
import reyes.reactive.mongo.repository.RxJavaPersonRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveMongoIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveMongoIntegrationTest.class);

    @Autowired
    private ReactiveFluentMongoOperations operationsRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private ReactiveCrudRepository reactiveRepository;

    @Autowired
    private RxJavaPersonRepository rxJavaRepository;

    @Before
    public void setUp() throws Exception {
        StepVerifier.create(reactiveMongoTemplate.dropCollection(Person.class)).verifyComplete();
        Flux<Person> insertAll = reactiveMongoTemplate
                .insertAll(
                        Flux.just(
                                new Person("John", "Rambo", 30),
                                new Person("Johny", "Bravo", 27))
                                .collectList());

        StepVerifier.create(insertAll).expectNextCount(2).verifyComplete();
    }

    @Test
    public void shouldInsertAndCountDataOverReactiveMongoTemplate() {
        Mono<Long> saveAndCount = reactiveMongoTemplate.count(new Query(), Person.class)
                .doOnNext(x -> LOGGER.info("count persons: " + x))
                .thenMany(reactiveMongoTemplate.insertAll(
                        Arrays.asList(
                                new Person("Anakin", "Skywalker", 23),
                                new Person("Han", "Solo", 37)
                        )
                )).last()
                .flatMap(v -> reactiveMongoTemplate.count(new Query(), Person.class))
                .doOnNext(x -> LOGGER.info("count persons: " + x));

        StepVerifier.create(saveAndCount).expectNext(4L).verifyComplete();
    }

    @Test
    public void shouldInsertAndCountDataOverReactiveMongoOperations() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Anakin", "Skywalker", 23));
        persons.add(new Person("Han", "Solo", 37));

        Mono<Long> saveAndCount = operationsRepository.query(Person.class)
                .count()
                .doOnNext(x -> LOGGER.info("count persons: " + x))
                .thenMany(
                        operationsRepository.insert(Person.class).all(persons)
                ).last()
                .flatMap(x -> operationsRepository.query(Person.class).count())
                .doOnNext((x -> LOGGER.info("count persons: " + x)));

        StepVerifier.create(saveAndCount).expectNext(4L).verifyComplete();
    }

    @Test
    public void shouldInsertAndCountDataOverReactiveRepository() {
        Mono<Long> saveAndCount = reactiveRepository.count()
                .doOnNext(x -> LOGGER.info("count persons: " + x))
                .thenMany(reactiveRepository.saveAll(
                        Flux.just(
                                new Person("Kylo", "Ren", 25),
                                new Person("Queen", "Leia", 23)
                        )
                ))
                .last()
                .flatMap(x -> reactiveRepository.count())
                .doOnNext(x -> LOGGER.info("count persons: " + x));

        StepVerifier.create(saveAndCount).expectNext(4L).verifyComplete();
    }

    @Test
    public void shouldInsertAndCountDataOverRxJavaRepository() {
        Flowable<Person> people = Flowable.just(
                new Person("Frodo", "Baggins", 20),
                new Person("Gandalf", "White", 2000)
        );

        rxJavaRepository.count()
                .doOnSuccess(x -> LOGGER.info("count persons: " + x))
                .toFlowable()
                .switchMap(x -> rxJavaRepository.saveAll(people))
                .lastElement()
                .toSingle()
                .flatMap(x -> rxJavaRepository.count())
                .doOnSuccess(x -> LOGGER.info("count persons: " + x))
                .test()
                .awaitCount(1)
                .assertValue(4L)
                .assertNoErrors()
                .awaitTerminalEvent();
    }
}