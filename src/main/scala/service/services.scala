package service

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import model.{Account, Position}
import model.models.{AccountFuture, PositionFuture}
import sttp.client.basicRequest
import sttp.model.StatusCode

import scala.concurrent.Future
import sttp.client._
import utils.JsonUtil._

trait HeaderTrait {
  def headers()(implicit ac:AppConfig):Map[String,String]
}


trait ServiceUtilTrait extends HeaderTrait with LazyLogging {

  def get[T](url:String, fn:String => T ):Future[Either[StatusCode, T]] =
  {
    import app.Implicits.sttpBackend
    import app.Implicits.ec
    val hRequest = basicRequest.headers(headers()).get(uri"${url}")

    hRequest.send().map(f => {
      logger.info(s"Call to url: $url, responded with: $f ")
      println(s"Call to url: $url, responded with: $f ")
      if (f.code.code == 200) {
        val responseBody: String = f.body.getOrElse("");
        println(s"Response from url response body :$responseBody:")
        val account: T = fn.apply(responseBody)
        println(s"account :$account:")
        Right(account)
      }
      else {
        logger.error(s"Call to url: $url, failed with : ${f.code} ")
        Left(f.code)
      }
    })
  }
}

trait Service[T] {
  def get(): Future[Either[StatusCode,T]]
}