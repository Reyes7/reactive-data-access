package reyes.reactive.scala.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import scala.beans.BeanProperty

@Document
case class Tweet (@BeanProperty @Id text : String, @BeanProperty author : Author){

  @BeanProperty
  var hashTags : Set[HashTag] = text.split(" ")
    .collect{
      case t if t.startsWith("#") => HashTag (t.replaceAll("[^#\\w]","").toLowerCase())
    }.toSet
}