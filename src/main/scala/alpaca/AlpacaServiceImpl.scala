package alpaca

import app.Implicits._
import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import config.AppConfig.config
import javax.inject.Inject
import model.{Account, Order, Orders, Position, Positions}
import service.{Service, ServiceUtilTrait}
import sttp.client._
import sttp.model.StatusCode
import utils.JsonUtil._

import scala.concurrent.Future

object AlpacaServiceImpl {

  def apply[T](url:String, fn: (String => T) )  = {
    new ServiceImpl(url, fn)
  }

  def positionUrl()(implicit ac:AppConfig):String = s"${config.alpacaConfig.baseUrl}/v2/positions"
  def accountUrl()(implicit ac:AppConfig):String = s"${config.alpacaConfig.baseUrl}/v2/account"
  def ordersUrl()(implicit ac:AppConfig):String = s"${config.alpacaConfig.baseUrl}/v2/orders"

  def fromStrToPosition(s:String):Positions = {
    Positions(s.fromString[Array[Position]])
  }

  def fromStrToAccount(s:String):Account = {
    s.fromString[Account]
  }

  def fromStrToOrders(s:String)= {
    Orders(s.fromString[Array[Order]])
  }

}

class ServiceImpl[T](url:String, fn: (String => T)) extends Service[T] with ServiceUtilTrait  with LazyLogging  {

  def headers()(implicit ac:AppConfig):Map[String,String] = Map(
    "APCA-API-KEY-ID"->ac.alpacaConfig.key,
    "APCA-API-SECRET-KEY" -> ac.alpacaConfig.secret
  )


  def get(): Future[Either[StatusCode, T]] = {
    get(url, fn)
  }

}