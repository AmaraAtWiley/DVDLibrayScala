package com.example

import com.example.DVDRegistry.ActionPerformed

//#json-formats
import spray.json.RootJsonFormat
import spray.json.DefaultJsonProtocol

object JsonFormats  {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val dvdJsonFormat: RootJsonFormat[DVD] = jsonFormat6(DVD.apply)
  implicit val dvdsJsonFormat: RootJsonFormat[DVDs] = jsonFormat1(DVDs.apply)

  implicit val actionPerformedJsonFormat: RootJsonFormat[ActionPerformed]  = jsonFormat1(ActionPerformed.apply)
}
//#json-formats
