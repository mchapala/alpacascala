package service

import alpaca.AlpacaServiceImpl
import model.{Account, Order, Orders}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class OrderServiceTest extends  AsyncFreeSpec with ScalaFutures with Matchers   {

  "OrderService" - {

    "should be able to deserialize response" in {
      import utils.JsonUtil._
      val jsonStr:String =
        """
          |[
          |  {
          |    "id": "e950ebbc-332d-4440-879f-456a09344ce7",
          |    "client_order_id": "aa75c5df-9696-461e-9115-cd1436f456e5",
          |    "created_at": "2020-04-24T21:07:11.978061Z",
          |    "updated_at": "2020-04-24T21:07:12.172684Z",
          |    "submitted_at": "2020-04-24T21:07:11.96935Z",
          |    "filled_at": null,
          |    "expired_at": null,
          |    "canceled_at": null,
          |    "failed_at": null,
          |    "replaced_at": null,
          |    "replaced_by": null,
          |    "replaces": null,
          |    "asset_id": "b28f4066-5c6d-479b-a2af-85dc1a8f16fb",
          |    "symbol": "SPY",
          |    "asset_class": "us_equity",
          |    "qty": "1",
          |    "filled_qty": "0",
          |    "filled_avg_price": null,
          |    "order_class": "",
          |    "order_type": "market",
          |    "type": "market",
          |    "side": "buy",
          |    "time_in_force": "gtc",
          |    "limit_price": null,
          |    "stop_price": null,
          |    "status": "new",
          |    "extended_hours": false,
          |    "legs": null
          |  }
          |]
          |
          |""".stripMargin

      val orders:Array[Order] = jsonStr.fromString[Array[Order]]

      orders.foreach(order => println(order))
      assert(orders.size==1)
    }

    "should get orders" in {
      val service = AlpacaServiceImpl[Orders](AlpacaServiceImpl.ordersUrl, AlpacaServiceImpl.fromStrToOrders)
      val accountFut = service.get()
      accountFut map {
        accountEither => accountEither match {
          case Right(value) => println(value); assert(true)
          case Left(value) =>  assert(fail)
        }
      }
    }
  }
}
