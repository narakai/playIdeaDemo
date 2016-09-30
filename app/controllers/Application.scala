package controllers

import java.io.File

import models.{Subscription, User}
import play.api.libs.json.{Json, JsValue}
import play.api.mvc._
import play.twirl.api.Html

class Application extends Controller {

  def main = Action {
    val content = Html("<div>This is the content for the sample app<div>")
    Ok(views.html.main("Home")(content))
  }

  def about = Action {
    val aboutContent = Html("<div>About this site<div>")
    Ok(views.html.about("About")(aboutContent))
  }

  //  def subscribe = Action {
  //    request =>
  //      val reqBody: AnyContent = request.body
  //      val textContent: Option[String] = reqBody.asText
  //      textContent.map {
  //        emailId =>
  //          Ok("added" + emailId + " to subscriber's list")
  //      }.getOrElse {
  //        BadRequest("improper request body")
  //      }
  //  }

  def subscribe = Action(parse.json) {
    request =>
      val reqData: JsValue = request.body
      val emailId = (reqData \ "emailId").as[String]
      val interval = (reqData \ "interval").as[String]
      Ok(s"added $emailId to subscriber's list and will send updates every $interval")
  }

  def subscribe = Action(parse.tolerantJson) {
    request =>
      val reqData: JsValue = request.body
      val emailId = (reqData \ "email").as[String]
      val interval = (reqData \ "interval").as[String]
      Ok(s"added $emailId to subscriber's list and will send updates every $interval")
  }

  def createProfile = Action(parse.multipartFormData) {
    request =>
      val formData = request.body.asFormUrlEncoded
      val email: String = formData.get("email").get.head
      val name: String = formData.get("name").get.head
      val userId: Long = User(email, name).save
      request.body.file("displayPic").map {
        picture =>
          val path = "/socialize/user/"
          if (!picture.filename.isEmpty) {
            picture.ref.moveTo(new File(path + userId + ".jpeg"))
          }
          Ok("successfully added user")
      }.getOrElse {
        BadRequest("failed to add user")
      }
  }

  val parseAsSubscription = parse.using {
    request =>
      parse.json.map {
        body =>
          val emailId: String = (body \ "emailId").as[String]
          val interval: String = (body \ "interval").as[String]
          Subscription(emailId, interval)
      }
  }

  implicit val subWrites = Json.writes[Subscription]

  def getSub = Action(parseAsSubscription) {
    request =>
      val subscription: Subscription = request.body
      Ok(Json.toJson(subscription))
  }
}