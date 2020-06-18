package model

import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty
import sttp.model.StatusCode

import scala.concurrent.Future

case class Account(
                    @JsonProperty("account_number") account_number: String,
                    @JsonProperty("buying_power") buying_power: Double,
                    @JsonProperty("regt_buying_power") regt_buying_power: Double,
                    @JsonProperty("cash") cash: Double,
                    @JsonProperty("portfolio_value") portfolio_value: Double,
                    @JsonProperty("long_market_value") long_market_value: Double,
                    @JsonProperty("short_market_value") short_market_value: Double,
                    @JsonProperty("initial_margin") initial_margin: Double,
                    @JsonProperty("maintenance_margin") maintenance_margin: Double
                  )

case class Position(symbol:String,
                    @JsonProperty("asset_class") assetClass:String,
                    @JsonProperty("avg_entry_price") entryPrice:String,
                    @JsonProperty("market_value") marketValue:String,
                    @JsonProperty("cost_basis") costBasis:String,
                    @JsonProperty("unrealized_pl") unrealizedProfitLoss:String,
                    @JsonProperty("current_price") currentPrice:String,
                    qty:Double,
                    side:String,
                   )

case class Positions(position: Array[Position])

case class Orders(orders:Array[Order])

case class Order(
                  @JsonProperty("client_order_id") clientOrderId:String,
                  @JsonProperty("filled_at") filledAt:Date,
                  symbol:String,
                  qty:Double,
                  side:String,
                  @JsonProperty("filled_qty") filledQty:Double,
                  @JsonProperty("filled_avg_price") filledAvgPrice:Double,
                  @JsonProperty("type") orderType:String,
                )

case class TickData(
                    symbol: String,
                    var price:Double,
                    var timestamp:Long
                   ) {

                      def setLast(last: Map[String,Object]): Unit ={
                        price = last("price").asInstanceOf[Double]
                        timestamp = last("timestamp").asInstanceOf[Long];
                      }
}

object models {
  type AccountFuture = Future[Either[StatusCode, Account]]
  type PositionFuture = Future[Either[StatusCode, Positions]]
}