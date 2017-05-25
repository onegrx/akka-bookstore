import java.util.regex.Pattern

import akka.actor.{Actor, ActorRef}

import scala.io.Source

/**
  * Created by onegrx on 25.05.17.
  */
case class SearchActor(nr: Int, sendTo: ActorRef) extends Actor {

  val path = getClass.getResource(s"/bookstore$nr.txt").getPath

  override def receive: Receive = {
    case SearchRequest(title) =>
      val book: Option[SearchResponse] = searchBook(title, sendTo)
      book.foreach(response => sender() ! response)
  }

  def searchBook(title: String, sendTo: ActorRef): Option[SearchResponse] = {
    for (line <- Source.fromFile(path).getLines()) {
      if (line.contains(title)) {
        val matcher = Pattern.compile("[0-9]+$").matcher(line)
        if(matcher.find()) {
          val price = matcher.group().toInt
          return Option(SearchResponse(title, price, sendTo))
        }
      }
    }
    None
  }
}
