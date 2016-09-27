package controllers

import play.api.mvc._
import play.twirl.api.Html

class Application extends Controller {

  def index = Action {
    val content = Html("<div>This is the content for the sample app<div>")
    Ok(views.html.main("Home")(content))
  }

  def about = Action {
    val aboutContent = Html("<div>About this site<div>")
    Ok(views.html.about("About")(aboutContent))
  }

}