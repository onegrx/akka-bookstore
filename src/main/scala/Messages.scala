import akka.actor.ActorRef

/**
  * Created by onegrx on 25.05.17.
  */

sealed trait Message

case class SearchRequest(title: String) extends Message
case class SearchResponse(title: String, price: Int, sender: ActorRef) extends Message
case class OrderRequest(title: String) extends Message
case class OrderResponse(title: String) extends Message