package reyes.reactive.scala

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestContextManager
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reyes.reactive.scala.model.{Author, Tweet}
import reyes.reactive.scala.repository.TweetRepository

@RunWith(classOf[SpringRunner])
@SpringBootTest
class ReactiveScalaIntegrationTest extends FunSuite with BeforeAndAfter with Matchers{

  @Autowired
  val tweetRepository : TweetRepository = null

  before{
    new TestContextManager(this.getClass).prepareTestInstance(this)

    val johnRambo = Author("johnRambo")
    val johnyBravo = Author("johnyBravo")
    val hanSolo = Author("hanSolo")

    val tweets = Flux.just(
      Tweet("Don't touch the hair! #hair #beautiful", johnyBravo),
      Tweet("#suprise Hello, 911 Emergency? There's a handsome guy in my bathroom! Hey, wait a second." +
        "Cancel that - it's only me! #glorious #superb #beautiful", johnyBravo),
      Tweet("#blame They drew first #blood, not me #die", johnRambo),
      Tweet("To #survive a #war, you gotta become #war.", johnRambo),
      Tweet("Great, #kid. Don't get #cocky", hanSolo),
      Tweet("Hokey religions and ancient #weapons are no match for a good #blaster at your side, kid.", hanSolo)
    )

    tweetRepository.deleteAll()
      .thenMany(tweetRepository.saveAll(tweets))
      .thenMany(tweetRepository.findAll())
      .subscribe((t : Tweet) => println(
        s"""=============================================================
          |@${t.author.handle}  ${t.hashTags}
          |${t.text}
        """.stripMargin
      ))
  }

  test("asd"){
    println("asd")
  }
}
