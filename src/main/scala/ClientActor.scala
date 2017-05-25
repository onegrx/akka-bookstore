import akka.actor.{Actor, Address}
import akka.actor.Actor.Receive

/**
  * Created by onegrx on 25.05.17.
  */
class ClientActor extends Actor {

  val path = "akka.tcp://remote_system@127.0.0.1:3552/user/remote"
  val selection = context.actorSelection(path)

  override def receive: Receive = {

    case request@SearchRequest(title) =>
      selection ! request

    case SearchResponse(title, price) =>
      println(s"Book found: $title, price is: $price")

  }
}
