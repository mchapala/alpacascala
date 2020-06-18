package service

import alpaca.AlpacaServiceImpl
import model.{Position, Positions, TickData}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import polygon.PolygonServiceImpl

class TickDataServiceTest  extends  AsyncFreeSpec with ScalaFutures with Matchers {

  "TickDataService" - {
    "Should Deserialize response to Last TickData" in {
      import utils.JsonUtil._
      val jsonStr =
        """
          {
          |  "last": {
          |    "exchange": 11,
          |    "price": 282.95,
          |    "size": 100,
          |    "timestamp": 1587763781260
          |  },
          |  "status": "success",
          |  "symbol": "SPY"
          |}
          |""".stripMargin
      val tickData:TickData = jsonStr.fromString[TickData]
      println(tickData)
      assert(tickData.symbol=="SPY")

    }

    "should get tick data" in {
      //(pending)
      val service = PolygonServiceImpl(PolygonServiceImpl.lastTickDataUrl(),"SPY" ,PolygonServiceImpl.fromStrToTickData)
      val tickDataFut = service.get
      tickDataFut map {
        either => either match {
          case Right(value:TickData) => println(value) ; assert(true)
          case Left(value) =>  assert(fail)
        }
      }
    }
  }
}