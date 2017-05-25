import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by onegrx on 25.05.17.
  */
class ServerActor extends Actor {

  val path = "akka.tcp://local_system@127.0.0.1:2552/user/local"
  val selection = context.actorSelection(path)

  val internal = ActorSystem("internal")


  override def receive: Receive = {

    case request@SearchRequest(_) =>
      println("Obtained: " + request)
      println("Sending to search actors")
      val sw1 = internal.actorOf(Props(SearchActor(1)))
      val sw2 = internal.actorOf(Props(SearchActor(2)))
      sw1 ! request
      sw2 ! request

    case response@SearchResponse(title, price) =>
      println("Got response from search actor")
      selection ! response

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
