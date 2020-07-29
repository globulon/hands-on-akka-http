package com.services.http.dtos

import io.github.typeness.spray.json.refined._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

trait JsonFormats {
  protected implicit val userCreation: RootJsonFormat[UserDescription] = jsonFormat1(UserDescription)
  protected implicit val userID: RootJsonFormat[UserID] = jsonFormat1(UserID)
  protected implicit val flatUser: RootJsonFormat[User] = jsonFormat2(User)
  protected implicit val noError: RootJsonFormat[NoError.type] = jsonFormat0(() ⇒ NoError)
}
