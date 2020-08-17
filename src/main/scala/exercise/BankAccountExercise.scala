package exercise

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import exercise.BankAccountExercise.TransactionType.TransactionType

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success}

object BankAccountExercise extends App {


  val actorSystem = ActorSystem("ActorSystem")
  val mahmoudActor = actorSystem.actorOf(Props(new BankAccount("Mahmoud")), "mahmoudActor")

  // case class
  val printerActor: ActorRef = actorSystem.actorOf(Props[printerActor], "printerActor")

  case class Transaction(transactionType: TransactionType, amount: BigDecimal)

  case class Deposit(amount: BigDecimal, replyTo: ActorRef)

  case class Withdrawal(amount: BigDecimal, replyTo: ActorRef)

  case class GetBalance(replyTo: ActorRef)

  case class GetStatement(replyTo: ActorRef)

  class BankAccount(private var name: String, private var balance: BigDecimal = 0) extends Actor {
    private var transactions: ListBuffer[Transaction] = ListBuffer()

    def getName: String = name

    def getBalance: BigDecimal = balance

    override def receive: Receive = {
      case Deposit(amount, replyTo) if amount < 0 => replyTo ! Failure(new IllegalArgumentException(s"Deposit amount is less than zero"))
      case Deposit(amount, replyTo) =>
        this.balance += amount
        transactions += Transaction(TransactionType.DEPOSIT, amount)
        replyTo ! Success(s"successfully deposit $amount")
      case Withdrawal(amount, replyTo) if amount < 0 => replyTo ! Failure(new IllegalArgumentException(s"Withdrawal amount is less than zero"))
      case Withdrawal(amount, replyTo) => this.balance -= amount
        transactions += Transaction(TransactionType.DEPOSIT, amount)
        replyTo ! Success(s"successfully withdrawal $amount")
      case GetBalance(replyTo) => replyTo ! Success(this.balance)
      case GetStatement(replyTo) => replyTo ! Success(this.transactions.toList)
    }
  }

  class printerActor extends Actor {
    override def receive: Receive = {
      case Success(transactions: List[Transaction]) => transactions.foreach(op => println(s" ${op.transactionType} | ${op.amount}"))
      case Success(msg) => println(msg)
      case Failure(ex) => println(ex.getMessage)
    }
  }

  object TransactionType extends Enumeration {
    type TransactionType = Value
    val DEPOSIT, WITHDRAWAL = Value
  }
  mahmoudActor ! Deposit(500, printerActor)
  mahmoudActor ! Deposit(750, printerActor)
  mahmoudActor ! Withdrawal(100, printerActor)
  mahmoudActor ! GetStatement(printerActor)
  mahmoudActor ! GetBalance(printerActor)

}
