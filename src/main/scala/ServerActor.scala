import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by onegrx on 25.05.17.
  */
class ServerActor extends Actor {

  val path = "akka.tcp://local_system@127.0.0.1:2552/user/local"
  val selection = context.actorSelection(path)

  val internal = ActorSystem("internal")


  override def receive: Receive = {

    case request@SearchRequest(_) =>
      val sendTo = sender()
      println("Obtained: " + request)
      println("Sending to search actors")
      val sw1 = internal.actorOf(Props(SearchActor(1, sendTo)))
      val sw2 = internal.actorOf(Props(SearchActor(2, sendTo)))
      sw1 ! request
      sw2 ! request

    case response@SearchResponse(title, price, sendTo) =>
      println("Got response from search actor")
      sendTo ! response

    case request@OrderRequest(_) =>
      println("Obtained: " + request)
      println("Sending to order actor")
      val orderActor = internal.actorOf(Props[OrderActor])
      orderActor ! request

    case response@OrderResponse(title) =>
      println("Got response from order actor")
      selection ! response
  }

}
