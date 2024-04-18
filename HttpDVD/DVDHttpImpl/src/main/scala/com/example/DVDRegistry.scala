package com.example

//#user-registry-actor
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import scala.collection.immutable

//#user-case-classes
final case class DVD(title: String, releaseYear: Int, mpaaRating: String, director: String, studio: String, userRating: String)
final case class DVDs(dvds: immutable.Seq[DVD])
//#user-case-classes

object DVDRegistry {
  // actor protocol
  sealed trait Command
  final case class GetDVDs(replyTo: ActorRef[DVDs]) extends Command
  final case class CreateDVD(dvd: DVD, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class GetDVD(title: String, replyTo: ActorRef[GetDVDResponse]) extends Command
  final case class DeleteDVD(title: String, replyTo: ActorRef[ActionPerformed]) extends Command

  final case class GetDVDResponse(maybeDVD: Option[DVD])
  final case class ActionPerformed(description: String)

  def apply(): Behavior[Command] = registry(Set.empty)

  private def registry(dvds: Set[DVD]): Behavior[Command] =
    Behaviors.receiveMessage {
      case GetDVDs(replyTo) =>
        replyTo ! DVDs(dvds.toSeq)
        Behaviors.same
      case CreateDVD(dvd, replyTo) =>
        replyTo ! ActionPerformed(s"DVD ${dvd.title} created.")
        registry(dvds + dvd)
      case GetDVD(title, replyTo) =>
        replyTo ! GetDVDResponse(dvds.find(_.title == title))
        Behaviors.same
      case DeleteDVD(title, replyTo) =>
        replyTo ! ActionPerformed(s"DVD $title deleted.")
        registry(dvds.filterNot(_.title == title))
    }
}
//#user-registry-actor
