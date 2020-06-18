package app

import alpaca.{AlpacaTrait, AlpacaServiceImpl}
import com.google.inject.{AbstractModule, Provides}
import config.AppConfig
import javax.inject.{Named, Singleton}
import model.{Account, Orders, Positions}
import service.Service

class AppModule extends AbstractModule  {

  override protected def configure(): Unit = {

  }

  @Provides
  @Named("account")
  @Singleton
  def accountService():Service[Account] = {
    AlpacaServiceImpl[Account](AlpacaServiceImpl.accountUrl, AlpacaServiceImpl.fromStrToAccount)
  }

  @Provides
  @Named("positions")
  @Singleton
  def positionService():Service[Positions] = {
    AlpacaServiceImpl[Positions](AlpacaServiceImpl.positionUrl, AlpacaServiceImpl.fromStrToPosition)
  }

  @Provides
  @Named("orders")
  @Singleton
  def orderService():Service[Orders] = {
    AlpacaServiceImpl[Orders](AlpacaServiceImpl.ordersUrl, AlpacaServiceImpl.fromStrToOrders)
  }

}