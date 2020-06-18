package service.sources

import akka.NotUsed
import akka.stream.scaladsl.Source
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}
import model.Positions
import model.models.{AccountFuture, PositionFuture}
import service.sources.GenericHttpSource.FutureT
import service.{ Service}
import sttp.model.StatusCode

import scala.concurrent.Future
import scala.concurrent.duration._

object GenericHttpSource {
  type FutureT[T] =  Future[Either[StatusCode,T]]
  def apply[T](intervalTime: Int, service: Service[T]): Source[FutureT[T], NotUsed] =
    Source.fromGraph(new GenericHttpSource(service)).throttle(1, FiniteDuration(1, SECONDS))
}

class GenericHttpSource[T](service: Service[T]) extends GraphStage[SourceShape[FutureT[T]]]  {

  val out: Outlet[Future[Either[StatusCode,T]]] = Outlet.create("alpaca source stream")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = {
    new GraphStageLogic(shape) {

      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          val account:Future[Either[StatusCode,T]] = service.get()
          push(out, account)
        }

        override def onDownstreamFinish(): Unit = {
          super.onDownstreamFinish()

          complete(out)
        }
      })
    }
  }

  override def shape: SourceShape[FutureT[T]] = SourceShape.of(out)
}