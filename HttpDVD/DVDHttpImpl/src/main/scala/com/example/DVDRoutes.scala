package com.example

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import scala.concurrent.Future
import com.example.DVDRegistry._
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout

//#import-json-formats
//#user-routes-class
class DVDRoutes(dvdRegistry: ActorRef[DVDRegistry.Command])(implicit val system: ActorSystem[_]) {

  //#user-routes-class
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import JsonFormats._
  //#import-json-formats

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout: Timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

  def getDVDs(): Future[DVDs] =
    dvdRegistry.ask(GetDVDs.apply)
  def getDVD(title: String): Future[GetDVDResponse] =
    dvdRegistry.ask(GetDVD(title, _))
  def createDVD(dvd: DVD): Future[ActionPerformed] =
    dvdRegistry.ask(CreateDVD(dvd, _))
  def deleteDVD(title: String): Future[ActionPerformed] =
    dvdRegistry.ask(DeleteDVD(title, _))

  //#all-routes
  //#users-get-post
  //#users-get-delete
  val dvdRoutes: Route =
    pathPrefix("dvds") {
      concat(
        //#users-get-delete
        pathEnd {
          concat(
            get {
              complete(getDVDs())
            },
            post {
              entity(as[DVD]) { dvd =>
                onSuccess(createDVD(dvd)) { performed =>
                  complete((StatusCodes.Created, performed))
                }
              }
            })
        },
        //#users-get-delete
        //#users-get-post
        path(Segment) { title =>
          concat(
            get {
              //#retrieve-user-info
              rejectEmptyResponse {
                onSuccess(getDVD(title)) { response =>
                  complete(response.maybeDVD)
                }
              }
              //#retrieve-user-info
            },
            delete {
              //#users-delete-logic
              onSuccess(deleteDVD(title)) { performed =>
                complete((StatusCodes.OK, performed))
              }
              //#users-delete-logic
            })
        })
      //#users-get-delete
    }
  //#all-routes
}
