package reyes.reactive.couchbase.repository;

import com.couchbase.client.java.Bucket;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reyes.reactive.couchbase.model.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveCouchbaseUserRepositoryTest {

    @Autowired
    private ReactiveCouchbaseUserRepository reactiveRepository;

    @Autowired
    private Bucket bucket;

    @Before
    public void setUp() throws Exception {
        Random random = new Random();

        User jRUser = new User("JR", "John", "Rambo", 0);
        User jBUser = new User("JB", "Johny", "Bravo", 0);

        reactiveRepository.saveAll(Arrays.asList(jBUser, jRUser)).subscribe();

        Flux.interval(Duration.ofSeconds(1L))
                .doOnNext(x -> {
                    int ramboHeartRate = random.nextInt(175 - 60) + 60;
                    int bravoHeartRate = random.nextInt(175 - 60) + 60;

                    if (ramboHeartRate > 120) {
                        bucket.mutateIn(jRUser.getId()).counter("activeMinutes", 1).execute();
                    }

                    if (bravoHeartRate > 120) {
                        bucket.mutateIn(jBUser.getId()).counter("activeMinutes", 1).execute();
                    }

                }).subscribe();
    }
}