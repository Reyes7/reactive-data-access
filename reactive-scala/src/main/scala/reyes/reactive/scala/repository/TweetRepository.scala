package reyes.reactive.scala.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reyes.reactive.scala.model.Tweet

trait TweetRepository extends ReactiveMongoRepository[Tweet, String]{

}