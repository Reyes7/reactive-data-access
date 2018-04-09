package reyes.reactive.scala.config

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class AkkaConfiguration {

  @Bean
  def actorSystem() = ActorSystem.create("reactiveScala")

  @Bean
  def actorMaterializer = ActorMaterializer.create(this.actorSystem())
}
