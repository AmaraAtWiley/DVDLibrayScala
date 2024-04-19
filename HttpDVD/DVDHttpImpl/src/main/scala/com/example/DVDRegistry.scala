package com.example

//#user-registry-actor
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.example.DVDRegistry.{driver, password, url, username}

import scala.collection.immutable
import java.sql.{Connection, DriverManager, ResultSet}

//#user-case-classes
final case class DVD(title: String, releaseYear: Int, mpaaRating: String, director: String, studio: String, userRating: String)
final case class DVDs(dvds: immutable.Seq[DVD])
//#user-case-classes

object DVDRegistry {

  def apply(): Behavior[Command] = registry(Set.empty)


  // Define database connection properties
  val url = "jdbc:mysql://localhost:3306/dvdLibrary?serverTimezone=America/Chicago&useSSL=false"
  val driver = "com.mysql.cj.jdbc.Driver"
  val username = "root"
  val password = "root"


  // actor protocol
  sealed trait Command
  final case class GetDVDs(replyTo: ActorRef[DVDs]) extends Command
  final case class CreateDVD(dvd: DVD, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class DeleteDVD(title : String, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class GetDVD(title: String, replyTo: ActorRef[GetDVDResponse]) extends Command

  final case class GetDVDResponse(maybeDVD: Option[DVD])
  final case class ActionPerformed(description: String)

  private def registry(dvds: Set[DVD]): Behavior[Command] = {
    // Initialize the database connection
    Class.forName(driver)
    val connection = DriverManager.getConnection(url, username, password)

    Behaviors.receiveMessage {
      case GetDVDs(replyTo) =>
        // Retrieve DVDs from the database
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM dvds")
        var dvdList = Set.empty[DVD]
        while (resultSet.next()) {
          val dvd = DVD(
            resultSet.getString("title"),
            resultSet.getInt("releaseYear"),
            resultSet.getString("mpaaRating"),
            resultSet.getString("director"),
            resultSet.getString("studio"),
            resultSet.getString("userRating")
          )
          dvdList += dvd
        }
        replyTo ! DVDs(dvdList.toSeq)
        Behaviors.same

      case CreateDVD(dvd, replyTo) =>
        // Insert a new DVD into the database
        val statement = connection.prepareStatement(
          "INSERT INTO dvds (title, releaseYear, mpaaRating, director, studio, userRating) VALUES (?, ?, ?, ?, ?, ?)"
        )
        statement.setString(1, dvd.title)
        statement.setInt(2, dvd.releaseYear)
        statement.setString(3, dvd.mpaaRating)
        statement.setString(4, dvd.director)
        statement.setString(5, dvd.studio)
        statement.setString(6, dvd.userRating)
        statement.executeUpdate()
        replyTo ! ActionPerformed(s"DVD ${dvd.title} created.")
        Behaviors.same

      case GetDVD(title, replyTo) =>
        val statement = connection.prepareStatement(
          "SELECT * FROM dvds WHERE title = ?"
        )
        statement.setString(1, title)
        val dvdResult = statement.executeQuery()
        if (dvdResult.next()){
        val dvd = DVD(
          dvdResult.getString("title"),
          dvdResult.getInt("releaseYear"),
          dvdResult.getString("mpaaRating"),
          dvdResult.getString("director"),
          dvdResult.getString("studio"),
          dvdResult.getString("userRating")
        )
        replyTo ! GetDVDResponse(Some(dvd))
        } else{
          replyTo ! GetDVDResponse(None)
        }
        Behaviors.same

      case DeleteDVD(title, replyTo) =>
        val statement = connection.prepareStatement(
          "DELETE FROM dvds WHERE title = ?"
        )
        statement.setString(1, title)
        val rowsAffected = statement.executeUpdate()
        if (rowsAffected == 1) {
          replyTo ! ActionPerformed(s"DVD $title deleted.")
        } else {
          replyTo ! ActionPerformed(s"DVD $title was not found.")
        }
        Behaviors.same
    }
  }
}
//#user-registry-actor
