import java.io.File

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn.readLine

/**
  * Created by onegrx on 25.05.17.
  */
object Client {

  val path = getClass.getResource("/client_app.conf").getPath
  val file: File = new File(path)
  val config = ConfigFactory.parseFile(file)

  val system = ActorSystem("local_system", config)

  val actor = system.actorOf(Props[ClientActor], name = "local")


  def main(args: Array[String]): Unit = {
    showMenu()

    while (true) {
      val line: String = readLine()

      line match {
        case "q" => System.exit(0)
        case "f" => actor ! SearchRequest(readLine("Book title: "))
        case _ => println("Invalid command")
      }
    }
  }

  def showMenu(): Unit = {
    println("Bookstore client")
    println("f - find a book with price")
    println("q - exit app")
  }
}