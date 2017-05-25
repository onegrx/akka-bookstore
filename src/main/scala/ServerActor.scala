import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by onegrx on 25.05.17.
  */
class ServerActor extends Actor {

  val path = "akka.tcp://local_system@127.0.0.1:2552/user/local"
  val selection = context.actorSelection(path)

  val internal = ActorSystem("internal")
  val sw1 = internal.actorOf(Props(SearchActor(1)), name = "searchWorker1")
  val sw2 = internal.actorOf(Props(SearchActor(2)), name = "searchWorker2")

  override def receive: Receive = {

    case request@SearchRequest(msg) =>
      println("Obtained: " + request)
      println("Sending to search actors")
      sw1 ! request
      sw2 ! request

    case response@SearchResponse(title, price) =>
      println("Got response from search actor")
      selection ! response

  }

}
