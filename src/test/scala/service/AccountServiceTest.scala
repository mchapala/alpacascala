package service

import alpaca.AlpacaServiceImpl
import model.Account
import org.scalatest.matchers.should.Matchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.{AnyFreeSpec, AsyncFreeSpec}

class AccountServiceTest extends  AsyncFreeSpec with ScalaFutures with Matchers   {

  "AccountService" - {

    "should be able to deserialize response" in {
      import utils.JsonUtil._
      val jsonStr:String =
        """
          |{
          |  "id": "632b4e47-db03-4d67-8b91-08724e8b6a19",
          |  "account_number": "PA2MGKBZ7YOU",
          |  "status": "ACTIVE",
          |  "currency": "USD",
          |  "buying_power": "399376.884",
          |  "regt_buying_power": "199412.52",
          |  "daytrading_buying_power": "399376.884",
          |  "cash": "99706.26",
          |  "portfolio_value": "100017.04",
          |  "pattern_day_trader": false,
          |  "trading_blocked": false,
          |  "transfers_blocked": false,
          |  "account_blocked": false,
          |  "created_at": "2020-04-02T22:24:15.579155Z",
          |  "trade_suspended_by_user": false,
          |  "multiplier": "4",
          |  "shorting_enabled": true,
          |  "equity": "100017.04",
          |  "last_equity": "100014.34",
          |  "long_market_value": "310.78",
          |  "short_market_value": "0",
          |  "initial_margin": "310.78",
          |  "maintenance_margin": "93.234",
          |  "last_maintenance_margin": "92.424",
          |  "sma": "0",
          |  "daytrade_count": 0
          |}
          |""".stripMargin

      val account:Account = jsonStr.fromString[Account]
      println(s"account: $account")
      assert(account.account_number=="PA2MGKBZ7YOU")
    }

    "should get account" in {

      val service = AlpacaServiceImpl[Account](AlpacaServiceImpl.accountUrl, AlpacaServiceImpl.fromStrToAccount)
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
