package service

import alpaca.AlpacaServiceImpl
import model.{Account, Position, Positions}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class PositionServiceTest  extends  AsyncFreeSpec with ScalaFutures with Matchers {

  "PositionService" - {

    "Should Deserialize response to Position" in {
      import utils.JsonUtil._
      val jsonStr =
        """
          |[
          |  {
          |    "asset_id": "b28f4066-5c6d-479b-a2af-85dc1a8f16fb",
          |    "symbol": "SPY",
          |    "exchange": "ARCA",
          |    "asset_class": "us_equity",
          |    "qty": "1",
          |    "avg_entry_price": "293.74",
          |    "side": "long",
          |    "market_value": "310.32",
          |    "cost_basis": "293.74",
          |    "unrealized_pl": "16.58",
          |    "unrealized_plpc": "0.0564444747055219",
          |    "unrealized_intraday_pl": "2.24",
          |    "unrealized_intraday_plpc": "0.0072708387431836",
          |    "current_price": "310.32",
          |    "lastday_price": "308.08",
          |    "change_today": "0.0072708387431836"
          |  }
          |]
          |""".stripMargin
      val positions:Array[Position] = jsonStr.fromString[Array[Position]]
      positions.foreach(s => println(s))
      assert(positions.size==1)

    }

    "should get position" in {
      val service = AlpacaServiceImpl[Positions](AlpacaServiceImpl.positionUrl, AlpacaServiceImpl.fromStrToPosition)
      val positionFut = service.get
      positionFut map {
        either => either match {
          case Right(value:Positions) => value.position.foreach(x => println(x)) ; assert(true)
          case Left(value) =>  assert(fail)
        }
      }
    }
  }
}
