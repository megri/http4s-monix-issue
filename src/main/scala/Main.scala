
import scala.concurrent.duration._

import cats.effect.ExitCode
import cats.implicits._

import monix.eval._
import monix.reactive._

import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.websocket._
import org.http4s.server.websocket.WebSocketBuilder

import fs2.interop.reactivestreams._


object Main extends TaskApp with Http4sDsl[Task] {
  def run(args: List[String]): Task[ExitCode] = {
    val nats = Observable.pure(0L).repeat.scanMap(_ + 1).delayOnNext(1.second).publish(scheduler)
    nats.connect()

    def routes: HttpRoutes[Task] = HttpRoutes.of {
      case GET -> Root => 
        nats.headL.flatMap(n => Ok(s"$n seconds has elapsed since server start"))

      case GET -> Root / "ws" =>
        WebSocketBuilder[Task].build(
          nats.map(n => WebSocketFrame.Text(s"tick: $n")).toReactivePublisher(scheduler).toStream[Task],
          _.drain
        )
      
      case GET -> Root / "test" =>
        Ok("I'm still alive!")
    }

    BlazeServerBuilder[Task]
      .bindHttp(host = "localhost", port = 8080)
      .withHttpApp(routes.orNotFound)
      .serve
      .compile
      .lastOrError
    }
}