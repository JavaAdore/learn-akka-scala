import akka.actor.{Actor, ActorSystem, Props}


case class Numbers(numbers: List[Int])


class MyTestActor extends Actor {
  override def receive: Receive = {
    case Numbers(numbers) => println(s"sum is ${numbers.sum}")
  }

}


object AskExample extends App {
  val system = ActorSystem("a")
  val actorRef = system.actorOf(Props[MyTestActor])
  actorRef ! Numbers(List[Int](1, 2, 3, 4, 5, 6))

}


