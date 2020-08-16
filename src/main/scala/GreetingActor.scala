import akka.actor.{Actor, ActorSystem, Props}

case class Greeting(name: String)

class GreetingActor extends Actor {


  override def receive: Receive = {

    case Greeting(name) => println(s"welcome ya $name")
    case _ => println("unknown message type")
  }

}


object Home extends App {

  val system = ActorSystem("greeter")

  val greetingsActorRef = system.actorOf(Props[GreetingActor])

  greetingsActorRef ! Greeting("Mahmoud")


}
