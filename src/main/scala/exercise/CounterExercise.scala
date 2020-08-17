package exercise

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object CounterExercise extends App {

  val actorSystem = ActorSystem("my-actor-system")
  val counterActorRef: ActorRef = actorSystem.actorOf(Props[CounterActor], "counterActor")

  class CounterActor extends Actor {
    var currentValue: Int = 0

    override def receive: Receive = {
      case Action.INCREMENT => currentValue += 1
      case Action.DECREMENT => currentValue -= 1
      case Action.PRINT => print(currentValue)

    }
  }

  object Action extends Enumeration {
    type Action = Value
    val INCREMENT, DECREMENT, PRINT = Value
  }
  counterActorRef ! Action.INCREMENT
  counterActorRef ! Action.INCREMENT
  counterActorRef ! Action.INCREMENT
  counterActorRef ! Action.INCREMENT
  counterActorRef ! Action.DECREMENT
  counterActorRef ! Action.PRINT


}
