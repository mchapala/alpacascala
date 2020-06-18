package polygon

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import config.AppConfig.config
import model.TickData
import service.ServiceUtilTrait
import sttp.model.StatusCode

import scala.concurrent.Future
import utils.JsonUtil._

object PolygonServiceImpl {

  def apply[T](url: String, stock: String, fn: (String => T)) = {
    new PolygonServiceImpl(url, stock, fn)
  }

  //https://api.polygon.io/_v1/last/stocks/SPY?apiKey=key
  def lastTickDataUrl()(implicit ac: AppConfig): String = s"${config.polygonConfig.baseUrl}/v1/last/stocks"

  def fromStrToTickData(s: String): TickData = {
    s.fromString[TickData]
  }

}


class PolygonServiceImpl[T](url: String, stock: String, fn: (String => T)) extends ServiceUtilTrait with LazyLogging {

  def headers()(implicit ac: AppConfig) = {
    Map[String, String]()
  }

  def requestHeaders()(implicit ac: AppConfig): String = {
    val apiKey = ac.polygonConfig.key
    s"?apiKey=$apiKey"
  }


  def get(): Future[Either[StatusCode, T]] = {
    val stockUrl = s"$url/$stock${requestHeaders}"
    get(stockUrl, fn)
  }

}
