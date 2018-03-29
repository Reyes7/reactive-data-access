package reyes.reactive.couchbase.model;

import com.couchbase.client.java.repository.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@Getter
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int activeMinutes;
}
