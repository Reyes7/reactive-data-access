package reyes.reactive.scala.service

import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import org.reactivestreams.Publisher
import org.springframework.stereotype.Service
import reyes.reactive.scala.model.{HashTag, Tweet}
import reyes.reactive.scala.repository.TweetRepository

@Service
class TweetService(tr : TweetRepository, am : ActorMaterializer) {

  def tweets() : Publisher[Tweet] =  tr.findAll()

  def hashTags() : Publisher[HashTag] = Source
    .fromPublisher(tweets())
    .map(t => t.hashTags)
    .reduce((a, b) => a ++ b)
    .mapConcat(identity)
    .runWith(Sink.asPublisher(true)){
      am
    }
}
