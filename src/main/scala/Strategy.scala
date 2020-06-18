package strategy

import akka.stream.{Inlet, Outlet, Shape}
import model.{Account, Order, Orders, Positions, TickData}
;

case class DCAStrategy (
                   account: Inlet[Account],
                   positions: Inlet[Positions],
                   orders: Inlet[Orders],
                   tickData: Inlet[TickData],
                   order: Outlet[Order]
                 ) extends Shape {


  override def inlets: Seq[Inlet[_]] = {

  }

  override def outlets: Seq[Outlet[_]] = {

  }

  override def deepCopy(): Shape = {

  }
}
