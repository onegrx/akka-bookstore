import java.io.File

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.io.Source

/**
  * Created by onegrx on 25.05.17.
  */
object Server extends App {

  val path = getClass.getResource("/server_app.conf").getPath
  val file = new File(path)
  val config = ConfigFactory.parseFile(file)

  val system = ActorSystem("remote_system", config)
  val actor = system.actorOf(Props[ServerActor], name = "remote")

  println("Server started. Listening for messages...")
}
