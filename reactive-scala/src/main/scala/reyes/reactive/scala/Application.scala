package reyes.reactive.scala

import org.springframework.boot.{ApplicationRunner, SpringApplication}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application{

}

object Application extends App{
  SpringApplication.run(classOf[Application], args: _*)
}