import java.io.FileWriter

import akka.actor.Actor
import akka.actor.Status.Success

import scala.io.Source

/**
  * Created by onegrx on 25.05.17.
  */
class OrderActor extends Actor {

  val path = "orders.txt"
  println(path)

  override def receive: Receive = {
    case OrderRequest(title) =>
      orderBook(title)
      sender() ! OrderResponse(title)
  }

  def orderBook(title: String) = {
    val fw = new FileWriter(path, true)
    try {
      fw.write(title)
    }
    finally fw.close()
  }
}
