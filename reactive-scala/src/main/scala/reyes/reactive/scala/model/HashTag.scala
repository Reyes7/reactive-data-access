package reyes.reactive.scala.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import scala.beans.BeanProperty

@Document
case class HashTag (@BeanProperty @Id tag : String)