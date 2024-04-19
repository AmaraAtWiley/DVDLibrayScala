package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.util.Failure
import scala.util.Success
import scala.concurrent.ExecutionContextExecutor

//#main-class
object QuickstartApp {
  //#start-http-server
  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    // Akka HTTP still needs a classic ActorSystem to start
    import system.executionContext

    val futureBinding = Http().newServerAt("localhost", 9000).bind(routes)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }
  //#start-http-server

  //#serve-static-html
  private def staticRoutes(implicit system: ActorSystem[_]): Route = {
    // Serve the HTML file located in the resources directory
    get {
      pathEndOrSingleSlash {
        getFromResource("static/index.html")
      } ~
        // Serve other static files if needed (e.g., CSS, JavaScript)
        pathPrefix("static") {
          getFromResourceDirectory("static")
        }
    }
  }
  //#serve-static-html

  def main(args: Array[String]): Unit = {
    //#server-bootstrapping
    val rootBehavior = Behaviors.setup[Nothing] { context =>
      val dvdRegistryActor = context.spawn(DVDRegistry(), "DVDRegistryActor")
      context.watch(dvdRegistryActor)

      val dvdRoutes = new DVDRoutes(dvdRegistryActor)(context.system)
      val combinedRoutes = dvdRoutes.dvdRoutes ~ staticRoutes(context.system)

      startHttpServer(combinedRoutes)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")
    //#server-bootstrapping
  }
}
//#main-class
