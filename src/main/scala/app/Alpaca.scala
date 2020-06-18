package app

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.google.inject.{Guice, Key, TypeLiteral}
import com.google.inject.name.Names
import model.Account
import model.models.AccountFuture
import service.{ Service}
import service.sources.GenericHttpSource
import sttp.client.HttpURLConnectionBackend
import sttp.client.akkahttp.AkkaHttpBackend

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Implicits {

  implicit val system: ActorSystem = ActorSystem("alpaca")
  implicit val mat: ActorMaterializer = ActorMaterializer();
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val sttpBackend = AkkaHttpBackend.usingActorSystem(system)

}


object Alpaca extends App  {

  import Implicits._
  val injector = Guice.createInjector(new AppModule());

  alpacaFlow()
  Thread.sleep(500)
  System.exit(0);


  def testFlow() = {
    val  source = Source(1 to 10)
    val sink1 = Sink.foreach[Int](println)

    val flow2 = Flow[Int].map(x => x * 2);
    flow2.runWith(source, sink1)
    source.limit(10).to(sink1).run()

  }

  def alpacaFlow() = {
    val sink2 = Sink.foreach[AccountFuture]( fut =>  fut.map(f => f match {
      case Right(value) => println(value)
      case Left(value) => println(value)
    }))

    //val service:Service[Account] = injector.getInstance(Key.get(classOf[Service[Account]], Names.named("account")));
    val service:Service[Account] = injector.getInstance(Key.get(new TypeLiteral[Service[Account]](){}, Names.named("account")));

    val alpacaAccountSource = GenericHttpSource[Account](100, service)
    alpacaAccountSource.to(sink2).run()
  }
}