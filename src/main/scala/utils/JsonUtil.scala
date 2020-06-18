package utils

import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}

object JsonUtil {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJsonStr(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  implicit class DefaultJsonWriter(value: Any)
  {
    implicit def toJson : JsonNode = {
      val sw = new java.io.StringWriter();
      mapper.writeValue(sw, value)
      mapper.readTree(sw.toString)
    }
  }

  implicit class DefaultJsonReader[T](json:JsonNode)
  {
    implicit def fromJson[T](implicit m : Manifest[T]): T = {
      mapper.readValue[T](json.toString)
    }
  }

  implicit class DefaultStringReader[T](json:String)
  {
    implicit def fromString[T](implicit m : Manifest[T]): T = {
      mapper.readValue[T](json)
    }
  }



}