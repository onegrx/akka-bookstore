import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by onegrx on 25.05.17.
  */
class ServerActor extends Actor {
  override def receive: Receive = {
    case SearchRequest(msg) => println("Obtained: " + msg)
  }
}
