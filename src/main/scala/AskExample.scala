import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.{ ask, pipe }
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global




case class Numbers(numbers:List[Int])


class MyTestActor extends Actor
{
  override def receive:Receive = {
    case Numbers(numbers) => sender() ! sum(numbers)
  }

  def sum(numbers:List[Int]) = {
    numbers.reduce(_+_)
  }
}


object AskExample extends App {
  implicit val timeout = Timeout(5 seconds) // needed for `?` below



  val system = ActorSystem("a");
 val actorRef =  system.actorOf(Props[MyTestActor])


  (actorRef ? Numbers(List[Int](1,2,3,4,5,6))).map(println(_))

}


