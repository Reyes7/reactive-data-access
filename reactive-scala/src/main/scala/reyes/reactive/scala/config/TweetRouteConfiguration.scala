package reyes.reactive.scala.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.reactive.function.server.RequestPredicates._
import org.springframework.web.reactive.function.server.RouterFunctions._
import org.springframework.web.reactive.function.server.ServerResponse._
import reyes.reactive.scala.model.{HashTag, Tweet}
import reyes.reactive.scala.service.TweetService

@Configuration
class TweetRouteConfiguration (tweetService: TweetService) {

  @Bean
  def routes() = route(GET("/tweets"), _ => ok().body(tweetService.tweets(), classOf[Tweet]))
    .andRoute(GET("/hashtags/unique"), _ => ok().body(tweetService.hashTags(), classOf[HashTag]))
}