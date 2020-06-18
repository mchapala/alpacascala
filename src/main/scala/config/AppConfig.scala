package config


import com.typesafe.config.ConfigFactory

object AppConfig {

  implicit val config:AppConfig = AppConfig.apply()

  implicit val alpacaConfig:AlpacaConfig = config.alpacaConfig

  def apply():AppConfig = {
    val conf = ConfigFactory.load();
    //conf.entrySet().forEach(es => print(s" Set:$es"))
    val alpacaConfig = conf.getConfig("alpaca")
    val alpaca: AlpacaConfig = AlpacaConfig(
      alpacaConfig.getString("url"),
      alpacaConfig.getString("key"),
      alpacaConfig.getString("secret")
    )
    val pc = conf.getConfig("polygon")
    val polygonConfig = PolygonConfig (
      pc.getString("url"),
      pc.getString("key")
    )



    AppConfig(alpaca,polygonConfig)
  }
}

case class AlpacaConfig(baseUrl:String, key:String, secret:String)
case class PolygonConfig(baseUrl:String, key:String)
case class AppConfig(alpacaConfig: AlpacaConfig, polygonConfig:PolygonConfig)
